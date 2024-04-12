package webSocketMessages.userCommands;

public class ResignGame extends UserGameCommand{
    public String gameID;
    public ResignGame(String authToken) {
        super(authToken);
    }
}
