package server.websocket;

import chess.ChessGame;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public Session session;
    public String authToken;
    public void joinConnection(String authToken, Session session) {
        this.authToken = authToken;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}
