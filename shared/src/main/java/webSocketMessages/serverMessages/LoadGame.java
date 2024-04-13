package webSocketMessages.serverMessages;

public class LoadGame extends Notification{
    public String game;

    public LoadGame(ServerMessageType type, String message, String game) {
        super(type, message);
        this.game = game;

    }

    public String getGame(){
        return game;
    }
}
