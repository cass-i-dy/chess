import exception.ResponseException;
import server.ServerFacade;
import ui.ServerClient;

import java.util.Scanner;

public class ConsolePreLogin {
    static Scanner scanner = new Scanner(System.in);
    static ServerClient serverClient;

    public static void start(ServerClient server) throws ResponseException {
        serverClient = server;
        loginDisplay();
    }


    public static void loginDisplay() throws ResponseException {
//        System.out.println("register <username> <password> <email>" );
//        System.out.println("login <username> <password>" );
//        System.out.println("quit" );
//        System.out.println("help" );
//        System.out.println("Option: ");
        String option = scanner.nextLine();
        String[] parts = option.split("\\s+");
        processLoginChoice(parts);
    }

    public static void processLoginChoice(String[] choice) throws ResponseException {
        switch (choice[0].toLowerCase()) {
            case "register":
                serverClient.register(choice);
                ConsolePostLogin.start(serverClient);
            case "login":
                serverClient.login(choice);
                ConsolePostLogin.start(serverClient);
            case "quit":
                break;
            case "help":
                System.out.println(serverClient.help());
                loginDisplay();

        }

    }
}
