package webSocketMessages.serverMessages;

public class ErrorMessage extends Notification{
    public String game;
    public String errorMessage;


    public ErrorMessage(ServerMessageType type, String message) {
        super(type, message);
        this.errorMessage = message;

    }
}
