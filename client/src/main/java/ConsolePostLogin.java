import exception.ResponseException;
import ui.ServerClient;

import java.util.Arrays;
import java.util.Scanner;

public class ConsolePostLogin {

    static Scanner scanner = new Scanner(System.in);

    static ServerClient serverClient;

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
            case "list":
                serverClient.list();
                gameDisplay();
            case "join":
                serverClient.join(option);
            case "observe":
                serverClient.join(option);
            case "logout":
                serverClient.logout(option);
                ConsolePreLogin.loginDisplay();
            case "quit":
                System.out.print("");
                break;
            case "help":
                System.out.println(serverClient.help(false));
                gameDisplay();
            default:
                System.out.print("Invalid Option");

        }
    }

}
