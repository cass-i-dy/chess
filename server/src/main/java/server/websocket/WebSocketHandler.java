package server.websocket;

import com.google.gson.Gson;
import exception.ResponseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
//import org.eclipse.jetty.websocket.client.io.ConnectionManager;
//import org.eclipse.jetty.websocket.client.io.ConnectionManager;
import webSocketMessages.serverMessages.Action;
import webSocketMessages.serverMessages.Notification;
//import ConnectionManager;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private final SessionManager connections = new SessionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        Action action = new Gson().fromJson(message, Action.class);
        switch (action.type()) {
            case JOIN -> join(action.gameID(), session);
//            case EXIT -> exit(action.visitorName());
        }
    }

    private void join(String gameID, Session session) throws IOException {
        connections.add(gameID, session);
        var message
                = String.format("%s is in the shop", gameID);
        var notification = new Notification(Notification.Type.ARRIVAL, message);
        connections.broadcast(gameID, notification);
    }

    private void exit(String gameID) throws IOException {
        connections.remove(gameID);
        var message = String.format("%s left the shop", gameID);
        var notification = new Notification(Notification.Type.DEPARTURE, message);
        connections.broadcast(gameID, notification);
    }

    public void makeNoise(String petName, String sound) throws ResponseException {
        try {
            var message = String.format("%s says %s", petName, sound);
            var notification = new Notification(Notification.Type.NOISE, message);
            connections.broadcast("", notification);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
