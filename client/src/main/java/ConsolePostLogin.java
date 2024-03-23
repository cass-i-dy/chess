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
        String option = scanner.nextLine().toUpperCase();
        String[] parts = option.split("\\s+");
        processGameChoice(parts);

    }

    public static void processGameChoice(String[] option) throws ResponseException {
        switch (option[0].toLowerCase()) {
            case "create":
                serverClient.create(option);
            case "list":
                serverClient.list();
            case "join":
                serverClient.join(option);
            case "observe":
                serverClient.join(option);
            case "logout":
                serverClient.logout(option);
            case "quit":
                System.out.print("");
            case "help":
                System.out.print("");
            default:
                System.out.print("Invalid Option");

        }
    }

}
