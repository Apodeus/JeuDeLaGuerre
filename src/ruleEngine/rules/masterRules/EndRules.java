package ruleEngine.rules.masterRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.CheckNoPriorityUnitAlly;
import ruleEngine.rules.atomicRules.CheckPlayerTurn;
import ruleEngine.rules.newRules.RuleCompositeAnd;

/**
 * Class testing if a player is allowed to end its turn, mainly if there is no of this player units that must retreat on its turn.
 * Perform the player switch if respected.
 */
public class EndRules extends RuleCompositeAnd {

    public EndRules(){
        super.add(new CheckPlayerTurn());
        super.add(new CheckNoPriorityUnitAlly());
    }

    public void applyResult(GameState state, GameAction action, RuleResult result) {
        state.switchPlayer();
    }
}
