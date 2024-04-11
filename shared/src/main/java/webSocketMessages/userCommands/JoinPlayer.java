package webSocketMessages.userCommands;

import chess.ChessGame;
import model.User;

public class JoinPlayer extends UserGameCommand{

    public String gameID;
    public ChessGame.TeamColor playerColor;
    public User user;

    public JoinPlayer(String authToken) {
        super(authToken);
    }

}
