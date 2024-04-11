package webSocketMessages.serverMessages;

public class Error extends Notification{
    public String game;
    public String errorMessage;


    public Error(ServerMessageType type, String message) {
        super(type, message);
        this.errorMessage = message;

    }
}
