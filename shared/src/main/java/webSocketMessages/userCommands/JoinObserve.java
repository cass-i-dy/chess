package webSocketMessages.userCommands;

import chess.ChessGame;
import model.User;

public class JoinObserve extends UserGameCommand{

        public String gameID;
        public ChessGame.TeamColor playerColor;
        public User user;

        public JoinObserve(String authToken) {
            super(authToken);
            this.commandType = CommandType.JOIN_OBSERVER;
        }
        public void setGameID(String gameID){
            this.gameID = gameID;
        }
        public void setUser(String username) {
            User tempUser = new User(username, "", "");
            this.user = tempUser;
        }

        public void setPlayerColor(ChessGame.TeamColor color){
            this.playerColor = color;
        }
    }
