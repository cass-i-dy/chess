package webSocketMessages.userCommands;

public class LeaveGame extends UserGameCommand{
    public String gameID;
    public LeaveGame(String authToken) {
        super(authToken);
    }
}
