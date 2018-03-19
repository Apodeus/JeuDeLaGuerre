package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

/**
 * Check if a unit is able to attack or not. With our rules interpretation, a unit is able to attack if and only if it already moved in the same turn.<br>
 * Valid if the unit can attack, invalid otherwise.
 *
 * @see ruleEngine.rules.masterRules.AttackRules
 */
public class CheckIsAttackingUnit extends Rule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        try {
            int x = action.getSourceCoordinates().getX();
            int y = action.getSourceCoordinates().getY();

            if (!state.getUnitType(x, y).isCanAttack()) {
                result.addMessage(this, "This unit is not suited to attack.");
                result.invalidate();
                return false;
            }
            return true;
        } catch (NullPointerException e){
            result.addMessage(this, "This unit is not suited to attack.");
            result.invalidate();
            return false;
        }
    }
}