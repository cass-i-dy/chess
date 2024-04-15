package websocket;

import webSocketMessages.serverMessages.Notification;

public class NotificationHandler {
    Notification notification;
    void notify(Notification notification){
        this.notification = notification;
//        System.out.println(notification.toString());
    }

    public Notification getNotification() {
        try {
        while (notification == null){
            Thread.sleep(10);
        }}
        catch (InterruptedException e) {
            System.out.println("Error");
        }
        return notification;
    }



}

