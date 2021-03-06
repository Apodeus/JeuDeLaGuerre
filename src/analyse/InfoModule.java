package analyse;

import analyse.mapMethods.IMetricsMapMethod;
import analyse.moveMethods.IMetricsMoveMethod;
import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.RuleChecker;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class InfoModule {

    private final static RuleChecker ruleChecker = new RuleChecker();

    public static double[][] getInfoMap(EMetricsMapType type, GameState gameState, EPlayer player) {
        IMetricsMapMethod m = type.getMethod();
        return m.compute(gameState, player);
    }

    public static Collection<MoveWrapper> getAvailableMoves(EMetricsMoveType type, GameState gameState, EPlayer player) {
        IMetricsMoveMethod method = type.getMethod();
        return method.compute(gameState, player);
    }

    public static Collection<Unit> getAllUnitsFromPlayer(GameState state, EPlayer player) {
        return state.getAllUnits().stream().filter((unit -> unit.getPlayer().equals(player))).collect(Collectors.toList());
    }

    public static RuleChecker getRuleChecker() {
        return ruleChecker;
    }

    public static double[][] initializeDoubleMap(GameState state, EPlayer player){
        double[][] result = new double[state.getWidth()][state.getHeight()];
        double startValue = (player.equals(EPlayer.PLAYER_SOUTH) ? 0.2 : 0.1);
        for (double[] d : result)
            Arrays.fill(d, startValue);

        return result;
    }
}
