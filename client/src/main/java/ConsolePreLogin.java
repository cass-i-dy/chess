import exception.ResponseException;
import ui.ServerClient;

import java.util.Scanner;

public class ConsolePreLogin {
    public static Scanner scanner = new Scanner(System.in);
    public static ServerClient serverClient;

    public static void start(ServerClient server) throws ResponseException {
        serverClient = server;
        loginDisplay();
    }


    public static void loginDisplay() throws ResponseException {
        System.out.println("type help to see login options");
        String option = scanner.nextLine();
        String[] parts = option.split("\\s+");
        processLoginChoice(parts);
    }

    public static void processLoginChoice(String[] choice) throws ResponseException {
        switch (choice[0].toLowerCase()) {
            case "register":
                if (serverClient.register(choice)){
                    System.out.println("In Game Menu");
                    ConsolePostLogin.start(serverClient);}
                else{loginDisplay();}
                break;
            case "login":
                if (serverClient.login(choice)) {
                System.out.println("In Game Menu");
                ConsolePostLogin.start(serverClient);}
                else{loginDisplay();}
                break;
            case "quit":
                break;
            case "help":
                System.out.println(serverClient.help(true));
                loginDisplay();
                break;
            case "game":
                ConsoleGame.start("white" );
                break;
            case "clear":
                serverClient.clear();
                break;
            default:
                System.out.println("Invalid Command");
                loginDisplay();
                break;

        }

    }
}
