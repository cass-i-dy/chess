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
import webSocketMessages.userCommands.*;
//import ConnectionManager;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private final SessionManager connections = new SessionManager();

    MySQLDataAccessGame gameAccess = new MySQLDataAccessGame();

    MySQLDataAccessAuth authAccess = new MySQLDataAccessAuth();

    Game diplayGame;


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
            case RESIGN -> resignGame(message, session);
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
            if (game.getVictor() != null){
                throw new WebsocketException("game over");
            }
            connections.joinAdd(authToken.getName(), joinPlayer.authToken, joinPlayer.gameID, joinPlayer.playerColor, session);

            var message
                    = String.format(authToken.getName() + " has joined as " + joinPlayer.playerColor);
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(authToken.getToken(), notification, joinPlayer.gameID);
            diplayGame = game;
            var gameObject = gameAccess.getGame(joinPlayer.gameID);
            String gameMessage = new Gson().toJson(gameObject, Game.class);
            var response = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, message, gameMessage);
            connections.broadcastOnce(authToken.getToken(), response, joinPlayer.gameID);
        } catch (Exception e) {
            connections.joinAdd("", joinPlayer.authToken, joinPlayer.gameID, joinPlayer.playerColor, session);
            var response = new Error(ServerMessage.ServerMessageType.ERROR, e.getMessage());
            connections.broadcastOnce(joinPlayer.authToken, response, joinPlayer.gameID);
            connections.remove(joinPlayer.gameID);}
    }

    private Game observe(String inputMessage, Session session) throws IOException {
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
            if (game.getVictor() != null){
                throw new WebsocketException("game over");
            }
            connections.observeAdd(authToken.getName(), joinPlayer.authToken, joinPlayer.gameID, session);
            var message
                    = String.format(authToken.getName() + " is observing ");
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(authToken.getToken(), notification, joinPlayer.gameID);
            diplayGame = game;
            var gameObject = gameAccess.getGame(joinPlayer.gameID);
            String gameMessage = new Gson().toJson(gameObject, Game.class);
            var response = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, message, gameMessage);
            connections.broadcastOnce(authToken.getToken(), response, joinPlayer.gameID);

        }
        catch (Exception e){
            connections.observeAdd("", joinPlayer.authToken, joinPlayer.gameID, session);
            var response = new Error(ServerMessage.ServerMessageType.ERROR, e.getMessage());
            connections.broadcastOnce(joinPlayer.authToken, response, joinPlayer.gameID);
            connections.remove(joinPlayer.gameID);
        }
        return diplayGame;
    }

    public Game makeMove(String inputMessage, Session session) throws IOException {
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
            String teamColor = null;
            if (game.getWhite().equals(authToken.getName())){
                teamColor = "WHITE";
            }
            if (game.getBlack().equals(authToken.getName())){
                teamColor = "BLACK";
            }
            if (teamColor == null){
                throw new WebsocketException("unauthorized");
            }
            if (game.getVictor() != null){
                throw new WebsocketException("game over");
            }
            if (!(game.getChessGame().getTeamTurn().toString().equals(teamColor))){
                throw new WebsocketException("unauthorized");
            }
            if (game.getChessGame().isInCheckmate(ChessGame.TeamColor.WHITE) || (game.getChessGame().isInCheckmate(ChessGame.TeamColor.BLACK))){
                game.setVictor("Game Over");
                gameAccess.updateGame(game);
                throw new WebsocketException("In CheckMate");
            }
            if (!(game.getChessGame().validMoves(possibleMove.move.getStartPosition()).contains(possibleMove.move))) {
                throw new WebsocketException("Move not valid");
            }
            game.getChessGame().makeMove(possibleMove.move);
            gameAccess.updateGame(game);
            diplayGame = game;
            var message
                    = String.format("Move was Made");
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(authToken.getToken(), notification, possibleMove.gameID);
            if (teamColor.equals("WHITE")){
            game.getChessGame().setTeamTurn(ChessGame.TeamColor.BLACK);}
            else{
                game.getChessGame().setTeamTurn(ChessGame.TeamColor.WHITE);}
            var gameObject = gameAccess.getGame(possibleMove.gameID);
            String gameMessage = new Gson().toJson(gameObject, Game.class);
            var response = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, message, gameMessage);
            connections.broadcast(authToken.getToken(), response, possibleMove.gameID);
            connections.broadcastOnce(authToken.getToken(), response, possibleMove.gameID);
        }

        catch (Exception e) {
            var response = new Error(ServerMessage.ServerMessageType.ERROR, e.getMessage());
            connections.broadcastOnce(possibleMove.authToken, response, possibleMove.gameID);
        }
        return diplayGame;
    }

    public void leaveGame(String inputMessage, Session session){
        LeaveGame leave= new Gson().fromJson(inputMessage, LeaveGame.class);
        try {
            AuthToken authToken = authAccess.findAuthToken(leave.authToken);
            if (authToken == null) {
                throw new WebsocketException("unauthorized");
            }
            Game game = gameAccess.getGame(leave.gameID);
            if (game == null) {
                throw new WebsocketException("gameID doesn't exist");
            }
            connections.leave(authToken.getToken(), leave.gameID);
            var message
                    = String.format(authToken.getName() + " has left");
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(authToken.getToken(), notification, leave.gameID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void resignGame(String inputMessage, Session session) throws IOException {
        ResignGame resign = new Gson().fromJson(inputMessage, ResignGame.class);
        try {
            AuthToken authToken = authAccess.findAuthToken(resign.authToken);
            if (authToken == null) {
                throw new WebsocketException("unauthorized");
            }
            Game game = gameAccess.getGame(resign.gameID);
            if (game == null) {
                throw new WebsocketException("gameID doesn't exist");
            }
            if (game.getVictor() != null){
                throw new WebsocketException("game over");
            }
            if (game.getWhite().equals(authToken.getName()) || game.getBlack().equals(authToken.getName())){
            game.setVictor("Game Over");
            gameAccess.updateGame(game);
            var message = String.format(authToken.getName() + " has resigned");
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(authToken.getToken(), notification, resign.gameID);
            connections.broadcastOnce(authToken.getToken(), notification, resign.gameID);}
            else{
                throw new WebsocketException("not allowed");
            }
        }
        catch (Exception e){
            var response = new Error(ServerMessage.ServerMessageType.ERROR, e.getMessage());
            connections.broadcastOnce(resign.authToken, response, resign.gameID);
        }}}


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
