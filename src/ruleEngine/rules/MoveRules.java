package ruleEngine.rules;

import game.board.Board;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.CheckPlayerTurn;

import java.util.LinkedList;
import java.util.List;

public class MoveRules implements IRule {

    private List<IRule> rules;
    private static IRule instance;

    private MoveRules(){
        rules = new LinkedList<>();

        //TODO: Put here the sub-rules (atomic) you need to check.
        rules.add(CheckPlayerTurn.getInstance());   //Maybe use an enum to get rid of getInstance()'s through implicit getValue() ? Or use static classes ? dunno
    }

    public static IRule getInstance() {
        if (instance == null)
            instance = new MoveRules();

        return instance;
    }

    @Override
    public boolean checkAction(Board board, GameAction action, RuleResult result) {
        boolean valid = true;
        for (IRule r : rules)
            valid = valid && r.checkAction(board, action, result);

        return valid;
    }
}