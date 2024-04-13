package server.websocket;
import chess.ChessGame;
import chess.ChessMove;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.Notification;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    public SessionManager() {

    }

    // add another hashmap to store connections
    public final ConcurrentHashMap<String, HashSet<Connection>> connections = new ConcurrentHashMap<>();

//    public final HashMap<String, Set<>>

    public void addPlayer(String authToken, String gameID, Session session){
        var connection = new Connection();
        connection.makeConnection(authToken, session);
        if (connections.get(gameID) == null) {
            connections.put(gameID, new HashSet<>());
        }
        connections.get(gameID).add(connection);
    }

    public void joinAdd(String username, String authToken, String gameID, ChessGame.TeamColor playerColor, Session session) {
        addPlayer(authToken, gameID, session);
    }

    public void observeAdd(String username, String authToken, String gameID, Session session) {
        addPlayer(authToken, gameID, session);
    }

    public void leave(String authToken, String gameID) {
        for (Set<Connection> game : connections.values()) {
            Iterator<Connection> iterator = game.iterator();
                while (iterator.hasNext()) {
                    Connection c = iterator.next();
                    if (c.session.isOpen() && c.authToken.equals(authToken)) {
                        // Remove the current Connection using the iterator's remove() method
                        iterator.remove();
                        System.out.println("Connection removed successfully");
                    }
                }
        }}


    public void remove(String gameID) {
        connections.remove(gameID);
    }

    public void broadcast(String excludeAuthToken, Notification notification, String gameID) throws IOException {
        cast(excludeAuthToken, notification, true, gameID);
    }

    public void cast(String excludeVisitorName, Notification notification, Boolean all, String gameID) throws IOException {
        var removeList = new ArrayList<Connection>();
        var gameConnections = connections.get(gameID);
        if (gameConnections != null){
            for (var c : gameConnections) {
                if (c.session.isOpen()) {
                    if (all){
                    if (!c.authToken.equals(excludeVisitorName)) {
                        c.send(notification.toString());
                    }}
                    else {
                        if (c.authToken.equals(excludeVisitorName)) {
                            c.send(notification.toString());
                        }
                    }
                } else {
                    removeList.add(c);
                }

            }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.authToken);
        }
    }}

    public void broadcastOnce(String excludeVisitorName, Notification notification, String gameID) throws IOException {
        cast(excludeVisitorName, notification, false, gameID);

    }
}

