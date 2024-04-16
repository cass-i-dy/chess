package websocket;


import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthToken;
import model.Game;
import webSocketMessages.serverMessages.*;

import javax.websocket.*;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.LeaveGame;
import webSocketMessages.userCommands.MakeMove;
import webSocketMessages.userCommands.ResignGame;


public class WebSocketFacade extends Endpoint{

    Session session;
    NotificationHandler notificationHandler;

    public AuthToken authToken;

    ChessGame game;


    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    Notification notification = new Gson().fromJson(message, Notification.class);
                    ServerMessage.ServerMessageType serverMessageType = notification.getServerMessageType();
                    if (notification.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)){
                        LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);
                        notificationHandler.loadGameNotify(loadGame);
                    }
                    if (notification.getServerMessageType().equals(ServerMessage.ServerMessageType.ERROR)){
                        ErrorMessage errorMessage  = new Gson().fromJson(message, ErrorMessage.class);
                        notificationHandler.errorNotify(errorMessage);
                    }
                    if (notification.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)){
                        notificationHandler.notify(notification);
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public ChessGame getGame(){
        return game;
    }

    public void joinUser(String gameID, ChessGame.TeamColor playerColor, AuthToken authToken) throws ResponseException{
        try {
            var joinPlayer = new JoinPlayer(authToken.getToken());
            joinPlayer.setGameID(gameID);
            joinPlayer.setUser(authToken.getName());
            joinPlayer.setPlayerColor(playerColor);

            this.authToken = authToken;
            this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayer));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void joinObserve(String gameID, ChessGame.TeamColor playerColor, AuthToken authToken) {
        try {
            var joinPlayer = new JoinPlayer(authToken.getToken());
            joinPlayer.setGameID(gameID);
            joinPlayer.setUser(authToken.getName());
            joinPlayer.setPlayerColor(playerColor);
            this.authToken = authToken;
            this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayer));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotion, AuthToken authToken){
        try {
            var make = new MakeMove(authToken.getToken());
            make.setChessMove(new ChessMove(startPosition, endPosition, promotion));
            this.authToken = authToken;
            this.session.getBasicRemote().sendText(new Gson().toJson(make));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void leave(String gameID) {
        try{
        var leaveGame = new LeaveGame(authToken.getToken());
        leaveGame.setGameID(gameID);
        this.session.getBasicRemote().sendText(new Gson().toJson(leaveGame));}
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resign(String gameID) {
        try {
            var resignGame = new ResignGame(gameID);
            resignGame.setGameID(gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(resignGame));}
        catch (IOException e) {
            throw new RuntimeException(e);
    }
    }
}
