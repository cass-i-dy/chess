package server.websocket;
import chess.ChessGame;
import chess.ChessMove;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    public SessionManager() {

    }

    // add another hashmap to store connections
    public final ConcurrentHashMap<String, HashSet<Connection>> connections = new ConcurrentHashMap<>();

//    public final HashMap<String, Set<>>

    public void joinAdd(String username, String authToken, String gameID, ChessGame.TeamColor playerColor, Session session) {
        var connection = new Connection();
        connection.Connection(authToken, session);
        if (connections.get(gameID) == null) {
            connections.put(gameID, new HashSet<>());
        }
        connections.get(gameID).add(connection);

    }

    public void observeAdd(String username, String authToken, String gameID, Session session) {
        var connection = new Connection();
        connection.Connection(authToken, session);
        if (connections.get(gameID) == null) {
            connections.put(gameID, new HashSet<>());
        }
        connections.get(gameID).add(connection);

    }



    public void remove(String gameID) {
        connections.remove(gameID);
    }

    public void broadcast(String excludeAuthToken, Notification notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var game : connections.values()) {
            for (var c : game) {
                if (c.session.isOpen()) {
                    if (!c.authToken.equals(excludeAuthToken)) {
                        c.send(notification.toString());
                    }
                } else {
                    removeList.add(c);
                }
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.authToken);
        }
    }

    public void broadcastOnce(String excludeVisitorName, Notification notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var game : connections.values()) {
            for (var c : game)
                if (c.session.isOpen()) {
                    if (c.authToken.equals(excludeVisitorName)) {
                        c.send(notification.toString());
                    }
                } else {
                    removeList.add(c);
                }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.authToken);
        }
    }
}

