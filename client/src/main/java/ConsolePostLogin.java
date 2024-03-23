import exception.ResponseException;
import ui.ServerClient;

import java.util.Scanner;

public class ConsolePostLogin {

    static Scanner scanner = new Scanner(System.in);

    static ServerClient serverClient;

    public static void start(ServerClient server) throws ResponseException {
        serverClient = server;
        gameDisplay();
    }


    public static void gameDisplay() throws ResponseException {
        String option = scanner.nextLine();
        String[] parts = option.split("\\s+");
        processGameChoice(parts);

    }

    public static void processGameChoice(String[] option) throws ResponseException {
        switch (option[0].toLowerCase()) {
            case "create":
                serverClient.create(option);
            case "list":
                System.out.print("");
            case "join":
                System.out.print("");
            case "observe":
                System.out.print("");
            case "logout":
                System.out.print("");
            case "quit":
                System.out.print("");
            case "help":
                System.out.print("");
            default:
                System.out.print("Invalid Option");

        }
    }

}
