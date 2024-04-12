package server.websocket;

import chess.ChessBoard;
import chess.ChessGame;
import chess.InvalidMoveException;
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
import webSocketMessages.userCommands.MakeMove;
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
            case JOIN_OBSERVER -> observe(message, session);
            case MAKE_MOVE -> makeMove(message, session);
            case LEAVE -> leaveGame(message, session);
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
            if (joinPlayer.playerColor == ChessGame.TeamColor.WHITE && !(game.getWhite().equals(authToken.getName()))) {
                throw new WebsocketException("White player not available");
            }
            if (joinPlayer.playerColor == ChessGame.TeamColor.BLACK && !(game.getBlack().equals(authToken.getName()))) {
                throw new WebsocketException("Black player not available");
            }
            if (game.getBlack() == null && game.getWhite() == null) {
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
            connections.broadcastOnce(authToken.getToken(), response);
        } catch (Exception e) {
            connections.joinAdd("", joinPlayer.authToken, joinPlayer.gameID, joinPlayer.playerColor, session);
            var response = new Error(ServerMessage.ServerMessageType.ERROR, "gameID doesn't exist");
            connections.broadcastOnce(joinPlayer.authToken, response);
            connections.remove(joinPlayer.gameID);
        }
    }

    private void observe(String inputMessage, Session session) throws IOException {
        JoinPlayer joinPlayer = new Gson().fromJson(inputMessage, JoinPlayer.class);
        try{
            AuthToken authToken = authAccess.findAuthToken(joinPlayer.authToken);
            if (authToken == null) {
                throw new WebsocketException("unauthorized");
            }
            Game game = gameAccess.getGame(joinPlayer.gameID);
            if (game == null) {
                throw new WebsocketException("gameID doesn't exist");
            }
            if (game.getBlack() == null && game.getWhite() == null) {
                throw new WebsocketException("Game not Valid");
            }
            connections.observeAdd(authToken.getName(), joinPlayer.authToken, joinPlayer.gameID, session);
            var message
                    = String.format(authToken.getName() + " is observing ");
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(authToken.getToken(), notification);

            var gameObject = gameAccess.getGame(joinPlayer.gameID);
            String gameMessage = new Gson().toJson(gameObject, Game.class);
            var response = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, message, gameMessage);
            connections.broadcastOnce(authToken.getToken(), response);

        }
        catch (Exception e){
            connections.observeAdd("", joinPlayer.authToken, joinPlayer.gameID, session);
            var response = new Error(ServerMessage.ServerMessageType.ERROR, "gameID doesn't exist");
            connections.broadcastOnce(joinPlayer.authToken, response);
            connections.remove(joinPlayer.gameID);
        }
    }

    public void makeMove(String inputMessage, Session session) throws IOException {
        MakeMove possibleMove = new Gson().fromJson(inputMessage, MakeMove.class);
        try {
            AuthToken authToken = authAccess.findAuthToken(possibleMove.authToken);
            if (authToken == null) {
                throw new WebsocketException("unauthorized");
            }
            Game game = gameAccess.getGame(possibleMove.gameID);
            if (game == null) {
                throw new WebsocketException("gameID doesn't exist");
            }
            if (game.getChessGame().isInCheckmate(ChessGame.TeamColor.WHITE) || (game.getChessGame().isInCheckmate(ChessGame.TeamColor.BLACK))){
                throw new WebsocketException("In CheckMate");
            }
            if (!(game.getChessGame().validMoves(possibleMove.move.getStartPosition()).contains(possibleMove.move))){
                throw new WebsocketException("Move not valid");
            }
            var message
                    = String.format("Move was Made");
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(authToken.getToken(), notification);

            var gameObject = gameAccess.getGame(possibleMove.gameID);
            String gameMessage = new Gson().toJson(gameObject, Game.class);
            var response = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, message, gameMessage);
            connections.broadcast(authToken.getToken(), response);
            connections.broadcastOnce(authToken.getToken(), response);


        }

        catch (Exception e) {
            var response = new Error(ServerMessage.ServerMessageType.ERROR, "gameID doesn't exist");
            connections.broadcastOnce(possibleMove.authToken, response);
        }

    }

    public void leaveGame(){

    }

}


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
