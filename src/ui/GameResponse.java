package ui;

import game.EPlayer;
import game.gameState.GameState;
import ui.commands.GameToUserCall;

public class GameResponse {
    private final GameState board;
    private final EPlayer player;
    private GameToUserCall response;
    private String message;

    public GameResponse(GameToUserCall response, String message, GameState gameState, EPlayer player) {
        this.response = response;
        this.message = message;
        this.board = gameState;
        this.player = player;
    }

    public GameToUserCall getResponse() {
        return response;
    }

    public void setResponse(GameToUserCall response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EPlayer getPlayer() {
        return player;
    }

    public GameState getBoard() {
        return board;
    }
}
