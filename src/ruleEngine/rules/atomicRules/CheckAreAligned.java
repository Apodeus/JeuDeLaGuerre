package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameMaster.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckAreAligned implements IRule {

    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates dst = action.getTargetCoordinates();

        int diffX = Math.abs(src.getX() - dst.getX());
        int diffY = Math.abs(src.getY() - dst.getY());

        if ( ( diffX != diffY ) && (diffX != 0) && (diffY != 0) ) {
            result.addMessage(this,
                    "The source is not aligned either horizontally, vertically or diagonally with the target.");
            result.invalidate();
            return false;
        }
        return true;
    }
}