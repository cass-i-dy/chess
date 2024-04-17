package webSocketMessages.userCommands;

public class LeaveGame extends UserGameCommand {
        public String gameID;
        public LeaveGame(String authToken) {
            super(authToken);
            this.commandType = CommandType.LEAVE;

        }

        public void setGameID(String gameID) {
            this.gameID = gameID;
        }
    }

