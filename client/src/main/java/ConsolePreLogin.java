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
            case "login":
                if (serverClient.login(choice)) {
                System.out.println("In Game Menu");
                ConsolePostLogin.start(serverClient);}
                else{loginDisplay();}
            case "quit":
                break;
            case "help":
                System.out.println(serverClient.help(true));
                loginDisplay();
            case "game":
                ConsoleGame.main(choice);

        }

    }
}
