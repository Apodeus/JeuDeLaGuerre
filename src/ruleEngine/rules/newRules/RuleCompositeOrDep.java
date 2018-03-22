package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

/**
 * Composite to be extended by super-set of atomic rules. Check the validity of all rules added
 * by {@link AbsRuleComposite#add(IRule)}. This composite represents the logical OR between
 * all IRule in {@link AbsRuleComposite#rules}, it checks all the rules while the result is false,
 * and stop when a result is valid. The order in which you add the rules is important.
 * {@link RuleCompositeAnd}.
 * @see AbsRuleComposite
 * @see IRule
 * @see RuleResult
 * @see ruleEngine.RuleChecker
 */
public class RuleCompositeOrDep extends AbsRuleComposite {

    public RuleCompositeOrDep(){
        super();
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if(rules.isEmpty())
            return true;
        boolean valid = false;
        RuleResult tmpResult = new RuleResult();
        for(IRule rule : rules){

            valid = rule.checkAction(state, action, tmpResult);
            if(valid) {
                return true;
            }
        }
        int len = tmpResult.getLogMessage().length();
        result.addMessage(tmpResult.getLogMessage().substring(0, len - (len > 2 ? 1 : 0)));
        result.invalidate();
        return valid;
    }

    public String toString(){
        if(rules.size() == 0){
            return this.getClass().getSimpleName();
        }
        StringBuilder str = new StringBuilder();
        str.append("(");
        for(int i = 0; i < rules.size() - 1; i++){
            str.append(rules.get(i).toString()).append(" ORdep ");
        }
        str.append(rules.get(rules.size()-1).toString()).append(")");

        return str.toString();
    }
}
