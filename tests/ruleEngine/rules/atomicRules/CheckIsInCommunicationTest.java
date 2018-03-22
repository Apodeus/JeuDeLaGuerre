package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckIsInCommunicationTest {

    private IBoard iBoard;
    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;

    @Before
    public void setUp() throws Exception {
        iBoard = mock(IBoard.class);
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
    }

    @Test
    public void checkActionMocking() {
        CheckIsInCommunication rule = new CheckIsInCommunication();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.INFANTRY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));

        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY_HORSE);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());


        //TODO: Need to discuss about the 2 following tests ... Not sure if it's still pertinent
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(false);
        assertTrue(!rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(!ruleResult.isValid());

        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY_HORSE);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(false);
        assertTrue(!rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(!ruleResult.isValid());

        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.INFANTRY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(false);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        String expectedMessage = "CheckIsInCommunication : This unit is not in your communication.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
        assertFalse(ruleResult.isValid());

        //TODO : Wrong behavior
        ruleResult = new RuleResult();
        when(iGameState.getUnitType(1, 1)).thenThrow(new NullPointerException());
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        expectedMessage = "CheckIsInCommunication : This unit is not in your communication.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
        assertFalse(ruleResult.isValid());

    }
}