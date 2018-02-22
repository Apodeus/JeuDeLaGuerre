package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameMaster.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

//Est ce que cette unité peut bouger
public class CheckCanMoveUnit implements IRule{
    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        boolean canMove = state.isUnitCanMove(src);
        if(!canMove){
            result.invalidate();
            result.addMessage(this, "This unit has already moved.");
            return false;
        }
        return true;
    }
}
