package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckIsAllyUnitTest {

    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckIsAllyUnit rule;

    @Before
    public void setUp() {
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        rule = new CheckIsAllyUnit();
    }

    @Test
    public void checkActionMockingCorrect() {
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(iGameState.isUnit(1, 1)).thenReturn(true);
        when(iGameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        when(iGameState.getUnitPlayer(1, 1)).thenReturn(EPlayer.PLAYER_NORTH);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrongPlayer() {
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(iGameState.isUnit(1, 1)).thenReturn(true);
        when(iGameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        when(iGameState.getUnitPlayer(1, 1)).thenReturn(EPlayer.PLAYER_SOUTH);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckIsAllyUnit : This unit is not owned by North Player.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongCoordinates() {
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(iGameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        when(iGameState.getUnitPlayer(1, 1)).thenReturn(EPlayer.PLAYER_NORTH);

        //TODO: To move on future CheckIsUnitTest
        ruleResult = new RuleResult();
        when(iGameState.isUnit(1, 1)).thenReturn(false);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckIsAllyUnit : There is no unit at (1, 1).\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

    }
}