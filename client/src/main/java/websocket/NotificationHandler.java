package websocket;

import webSocketMessages.serverMessages.Notification;

public class NotificationHandler {
    Notification notification;
    void notify(Notification notification){
        this.notification = notification;

    }

    public Notification getNotification(){
        return notification;
    }



}

