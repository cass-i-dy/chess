package websocket;


import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthToken;
import webSocketMessages.serverMessages.*;

import javax.websocket.*;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.MakeMove;


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
                    notificationHandler.notify(notification);
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
            joinPlayer.setUser(authToken.getToken());
            joinPlayer.setPlayerColor(playerColor);

            this.authToken = authToken;
            this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayer));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void joinObserve(String gameID, String playerColor, AuthToken authToken) {
        try {
            var joinPlayer = new JoinPlayer(authToken.getToken());
            this.authToken = authToken;
            this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayer));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove(int row, int col, AuthToken authToken){
        try {
            var make = new MakeMove(authToken.getToken());
            this.authToken = authToken;
            this.session.getBasicRemote().sendText(new Gson().toJson(make));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
