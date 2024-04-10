package websocket;


import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthToken;
import webSocketMessages.serverMessages.*;

import javax.websocket.*;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

import webSocketMessages.userCommands.UserGameCommand;


public class WebSocketFacade extends Endpoint{

    Session session;
    NotificationHandler notificationHandler;


    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
//                @Override
                public void onMessage(String message) {
                    Notification notification = new Gson().fromJson(message, Notification.class);
                    notificationHandler.notify(notification);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

//    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void joinUser(String gameID, String playerColor, AuthToken authToken) throws ResponseException{
        try {
            var userGameCommand = new UserGameCommand(authToken.getName());
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
