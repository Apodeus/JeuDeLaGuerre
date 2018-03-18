package ruleEngine.rules.masterRules;

import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import ruleEngine.rules.atomicRules.*;

import java.util.List;

/**
 * Class testing if a unit move is allowed according to its range of movement, the terrain and its communication supplying.
 * Performs the move on the board if respected.
 */
public class MoveRules extends MasterRule {

    public MoveRules() {
        //TODO: Put here the sub-rules (atomic) you need to check.
        addRule(new CheckPlayerTurn());
        addRule(new CheckPlayerMovesLeft());
        addRule(new CheckOnBoard());
        addRule(new CheckAbilityToMove());//comm
        addRule(new CheckIsAllyUnit());
        addRule(new CheckIsPriorityUnit());
        //addDependence(new CheckNoPriorityUnitAlly(), new CheckIsPriorityUnit(), );
        addRule(new CheckCanMoveUnit());
        addRule(new CheckUnitMP());
        addDependence(new CheckUnitMP(), new CheckIsAllyUnit(), true);
        addRule(new CheckIsEmptyPath());
    }

    @Override
    public void applyResult(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates target = action.getTargetCoordinates();
        state.removePriorityUnit(src);

        state.setUnitHasMoved(src);
        //state.updateUnitPosition(src, target);
        state.removeOneAction();
        state.moveUnit(src.getX(), src.getY(), target.getX(), target.getY());

        EUnitData movingUnit = state.getUnitType(target.getX(), target.getY());
        if (movingUnit.isCanAttack()) {
            List<Building> buildings = state.getAllBuildings();
            for (Building building : buildings) {
                if (building.getPlayer() != action.getPlayer()
                        && building.getBuildingData() == EBuildingData.ARSENAL
                        && building.getX() == target.getX()
                        && building.getY() == target.getY()) {
                    state.removeBuilding(building);
                    break;
                }
            }
        }
    }
}
