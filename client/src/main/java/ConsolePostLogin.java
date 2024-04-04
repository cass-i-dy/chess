import exception.ResponseException;
import ui.ServerClient;

import java.util.Arrays;
import java.util.Scanner;

public class ConsolePostLogin {

    public static Scanner scanner = new Scanner(System.in);
    public static ServerClient serverClient;

    public static void start(ServerClient server) throws ResponseException {
        serverClient = server;
        gameDisplay();
    }


    public static void gameDisplay() throws ResponseException {
        System.out.println("type help to see game options");
        String option = scanner.nextLine();
        String[] parts = option.split("\\s+");
        processGameChoice(parts);
    }

    public static void processGameChoice(String[] option) throws ResponseException {
        switch (option[0].toLowerCase()) {
            case "create":
                serverClient.create(option);
                gameDisplay();
                break;
            case "list":
                System.out.println(serverClient.list());
                gameDisplay();
                break;
            case "join", "observe":
                if(serverClient.join(option)){
                ConsoleGame.start(option[2]);}
                else{gameDisplay();}
                break;
            case "logout":
                serverClient.logout(option);
                ConsolePreLogin.loginDisplay();
                break;
            case "help":
                System.out.println(serverClient.help(false));
                gameDisplay();
                break;
            default:
                System.out.print("Invalid Option");

        }
    }

}
