package webSocketMessages.serverMessages;

import com.google.gson.Gson;

public class Notification extends ServerMessage {
    ServerMessage.ServerMessageType type;
    String message;

    public Notification(ServerMessageType type, String message) {
        super(type);
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return new Gson().toJson(this);
    }}

