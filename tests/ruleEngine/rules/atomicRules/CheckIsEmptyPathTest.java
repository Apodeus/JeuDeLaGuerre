package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Board;
import game.board.BoardManager;
import game.board.IBoardManager;
import game.board.entity.EBuilding;
import game.board.entity.EUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ruleEngine.*;
import ruleEngine.exceptions.IncorrectGameActionException;

import static org.junit.Assert.*;

public class CheckIsEmptyPathTest {

    private GameAction gameAction;
    private IBoardManager master;
    RuleChecker moveRules;

    //TODO: Tests should be better ...
    @Before
    public void setUp(){
        master = BoardManager.getInstance();
        master.initBoard(25, 20);

        master.addBuilding(EBuilding.MOUNTAIN, EPlayer.PLAYER1,0, 1); ///////////////
        master.addBuilding(EBuilding.MOUNTAIN, EPlayer.PLAYER1,1, 0); // C  M  -  -
        master.addBuilding(EBuilding.MOUNTAIN, EPlayer.PLAYER1,1, 1); // M  -  -  -
                                                                           // -  M  -  -
        master.addUnit(EUnit.CAVALRY, EPlayer.PLAYER1,0, 0);
        master.setCommunication(EPlayer.PLAYER1, 0, 0, true);

        gameAction = new GameAction(EPlayer.PLAYER1, EGameActionType.MOVE);

        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        moveRules = RuleChecker.getInstance();
    }

    @Test
    public void isEmptyPath() throws IncorrectGameActionException {
        System.out.println(master.getBoard().toString());
        System.out.println(moveRules.checkAction(master.getBoard(), gameAction).getLogMessage());
    }

}