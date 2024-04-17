package ui;

import exception.ResponseException;
import ui.ConsoleGame;
import ui.ServerClient;

import java.util.Scanner;

public class ConsolePostLogin {

    public static Scanner scanner = new Scanner(System.in);
    public static ServerClient serverClient;

    public static void start(ServerClient server) throws ResponseException {
        serverClient = server;
        gameDisplay();
    }


    public static void gameDisplay() throws ResponseException {
        System.out.println("type help to see menu options");
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
            case "join":
                if(serverClient.join(option)){
                    ConsoleGame.start(serverClient, option[1], option[2], true);
                    break;
                }
                else{gameDisplay();}
                break;
            case "observe":
                if(serverClient.join(option)){
                    ConsoleGame.start(serverClient, option[1], "WHITE", false);
                    break;
                }
                else{gameDisplay();}
                break;
            case "logout":
                serverClient.logout(option);
                ConsolePreLogin.loginDisplay();
                break;
            case "help":
                System.out.println(serverClient.help("menu"));
                gameDisplay();
                break;
            default:
                System.out.print("Invalid Option");

        }
    }

}
