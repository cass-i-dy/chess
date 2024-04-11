package webSocketMessages.userCommands;

import chess.ChessGame;
import model.AuthToken;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    public UserGameCommand(String authToken) {
        this.authToken = authToken;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;

    public final String authToken;

//    public String gameID;
//    public ChessGame.TeamColor playerColor;

    public String getAuthString() {
        return authToken;
    }

//    public String getUsername() {
//        return authToken.getName();
//    }

    public CommandType getCommandType() {
        return this.commandType;
    }

//    public void setGameID(String gameID){
//        this.gameID = gameID;
//    }
//
//    public void setPlayerColor(ChessGame.TeamColor playerColor){
//        this.playerColor = playerColor;
//    }
//    public String getGameID(){
//        return this.gameID;
//    }
//
//    public ChessGame.TeamColor getPlayerColor(){
//        return this.playerColor;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}
