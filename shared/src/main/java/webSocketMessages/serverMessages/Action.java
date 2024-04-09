package webSocketMessages.serverMessages;

import com.google.gson.Gson;

public record Action(Type type, String gameID) {
    public enum Type {
        JOIN,
        LOGGEDIN
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
