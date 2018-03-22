package player;

import analyse.EMetricsMoveType;
import analyse.InfoModule;
import analyse.MoveWrapper;
import game.EPlayer;
import game.Game;
import game.gameState.GameState;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import ui.GameResponse;
import ui.UIAction;
import ui.commands.UserToGameCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BotPlayer implements Player {
    private Thread gameThread;
    private Thread guiThread;
    private UIAction action;
    private GameResponse response;
    private Object wait_command;
    private Object wait_response;
    private RuleChecker ruleChecker;

    public BotPlayer(Thread gameThread, Thread guiThread){
        this.gameThread = gameThread;
        this.guiThread = guiThread;
        action = null;
        response = null;
        wait_command = new Object();
        wait_response = new Object();
        ruleChecker = new RuleChecker();
    }

    private UIAction createCommand(){
        List<GameAction> actionList = new ArrayList<>();
//        GameState gameState = Game.getInstance().getGameState();
//        List<Unit> unitList = gameState.getAllUnits();
//        for(Unit unit : unitList){
//            for(int i = -unit.getUnitData().getMovementValue(); i <= unit.getUnitData().getMovementValue(); ++i ){
//                for(int j = -unit.getUnitData().getMovementValue(); j <= unit.getUnitData().getMovementValue(); ++j ) {
//                    //if (gameState.getUnitPlayer(unit.getX(), unit.getY()) == gameState.getActualPlayer()) {
//                    GameAction action = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
//                    action.setSourceCoordinates(unit.getX(), unit.getY());
//                    action.setTargetCoordinates(unit.getX() + i, unit.getY() + j);
//                    try {
//                        if (ruleChecker.checkAction(gameState, action)) {
//                            System.out.println("valid");
//                            actionList.add(action);
//                        }
//                        System.out.println(action.getSourceCoordinates().toString() + " -> " + action.getTargetCoordinates().toString());
//                    } catch (IncorrectGameActionException ignore) {
//                    }
//                    //}
//                }
//            }
//        }
        GameState state = Game.getInstance().getGameState();
        Collection<MoveWrapper> moves = InfoModule.getAvailableMoves(EMetricsMoveType.ALL_AVAILABLE_MOVES, state, state.getActualPlayer());
        for (MoveWrapper mw : moves){
            GameAction ga = new GameAction(state.getActualPlayer(), EGameActionType.MOVE);
            ga.setSourceCoordinates(mw.getSourceCoordinates().getX(), mw.getSourceCoordinates().getY());
            ga.setTargetCoordinates(mw.getTargetCoordinates().getX(), mw.getTargetCoordinates().getY());
            actionList.add(ga);
        }
        System.out.println("Possible move : " + actionList.size());
        if(actionList.size() == 0) return new UIAction(UserToGameCall.GAME_ACTION, new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.END_TURN));
        else {
            GameAction action = actionList.get((int)(Math.random()*actionList.size()));
            return new UIAction(UserToGameCall.GAME_ACTION, action);
        }
    }

    @Override
    public void setCommand(UIAction action){
        //assert Thread.currentThread() == guiThread;
        if(action.getCommand() == UserToGameCall.CMD_ERROR)
            this.action = createCommand();
        else
            this.action = action;

        synchronized (wait_command) {
            wait_command.notifyAll();
        }
    }

    @Override
    public UIAction getCommand(){
        assert Thread.currentThread() == gameThread;
        createCommand();
        UIAction output;
        synchronized (wait_command) {
            while (action == null) {
                try {
                    wait_command.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
        output = action;
        action = null;
        return output;
    }

    @Override
    public void setResponse(GameResponse response){
        assert Thread.currentThread() == gameThread;
        this.response = response;
        synchronized (wait_response) {
            wait_response.notifyAll();
        }
    }

    @Override
    public GameResponse getResponse(){
        //assert Thread.currentThread() == guiThread;
        GameResponse output;
        synchronized (wait_response) {
            while (response == null) {
                try {
                    wait_response.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
        output = response;
        response = null;
        return output;
    }
}
