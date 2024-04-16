package websocket;

import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;

public class NotificationHandler {
    protected Notification notification;

//    public void notify(){
//
//    }
//
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


    public void notify(Notification notification) {
    }

    public void loadGameNotify(LoadGame loadGame){

    }

    public void errorNotify(ErrorMessage errorMessage){

    }
}

