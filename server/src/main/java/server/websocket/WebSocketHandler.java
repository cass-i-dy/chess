package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MySQLDataAccessAuth;
import dataAccess.MySQLDataAccessGame;
import exception.ResponseException;
import exception.WebsocketException;
import model.AuthToken;
import model.Game;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
//import org.eclipse.jetty.websocket.client.io.ConnectionManager;
//import org.eclipse.jetty.websocket.client.io.ConnectionManager;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;
//import ConnectionManager;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private final SessionManager connections = new SessionManager();

    MySQLDataAccessGame gameAccess = new MySQLDataAccessGame();

    MySQLDataAccessAuth authAccess = new MySQLDataAccessAuth();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        UserGameCommand userGameCommand = null;
        try {
            userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> join(message, session);
            case JOIN_OBSERVER -> {
            }
            case MAKE_MOVE -> {
            }
            case LEAVE -> {
            }
            case RESIGN -> {
            }
            default -> {
            }
        }
    }

    private void join(String inputMessage, Session session) throws IOException, DataAccessException {
        JoinPlayer joinPlayer = new Gson().fromJson(inputMessage, JoinPlayer.class);
        try {
            AuthToken authToken = authAccess.findAuthToken(joinPlayer.authToken);
            if (authToken == null) {
                throw new WebsocketException("unauthorized");
            }
            Game game = gameAccess.getGame(joinPlayer.gameID);
            if (game == null) {
                throw new WebsocketException("gameID doesn't exist");
            }
            if (joinPlayer.playerColor == ChessGame.TeamColor.WHITE && game.getWhite() != null){
                throw new WebsocketException("White player not available");
            }
            if (joinPlayer.playerColor == ChessGame.TeamColor.BLACK && game.getBlack() != null){
                throw new WebsocketException("Black player not available");
            }
            if (game.getBlack() == null && game.getWhite() == null){
                throw new WebsocketException("Game not Valid");
            }
            connections.joinAdd(authToken.getName(), joinPlayer.authToken, joinPlayer.gameID, joinPlayer.playerColor, session);

            var message
                    = String.format(authToken.getName() + " has joined as " + joinPlayer.playerColor);
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(authToken.getToken(), notification);

            var gameObject = gameAccess.getGame(joinPlayer.gameID);
            String gameMessage = new Gson().toJson(gameObject, Game.class);
            var response = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, message, gameMessage);
//        var gameUpdateNotification = new Notification(ServerMessage.ServerMessageType.LOAD_GAME, gameMessage);
            connections.broadcastOnce(authToken.getToken(), response);
        } catch (Exception e) {
            connections.joinAdd("", joinPlayer.authToken, joinPlayer.gameID, joinPlayer.playerColor, session);
            var response = new Error(ServerMessage.ServerMessageType.ERROR, "gameID doesn't exist");
            connections.broadcastOnce(joinPlayer.authToken, response);
            connections.remove(joinPlayer.gameID);
        }
    }}


//    private void exit(String gameID) throws IOException {
//        connections.remove(gameID);
//        var message = String.format("%s left the shop", gameID);
//        var notification = new Notification(Notification.Type.DEPARTURE, message);
//        connections.broadcast(gameID, notification);
//    }
//
//    public void makeNoise(String petName, String sound) throws ResponseException {
//        try {
//            var message = String.format("%s says %s", petName, sound);
//            var notification = new Notification(Notification.Type.NOISE, message);
//            connections.broadcast("", notification);
//        } catch (Exception ex) {
//            throw new ResponseException(500, ex.getMessage());
//        }
//    }
//}
