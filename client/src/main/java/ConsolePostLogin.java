import java.util.Scanner;

public class ConsolePostLogin {

    static Scanner scanner = new Scanner(System.in);

    public static void gameDisplay() {
        System.out.printf("%d. %s%n", 1, "create" );
        System.out.printf("%d. %s%n", 2, "list" );
        System.out.printf("%d. %s%n", 3, "join" );
        System.out.printf("%d. %s%n", 4, "observe" );
        System.out.printf("%d. %s%n", 5, "logout" );
        System.out.printf("%d. %s%n", 6, "quit" );
        System.out.printf("%d. %s%n", 7, "help" );
        System.out.print("Option: ");
        int option = scanner.nextInt();
        processGameChoice(option);

    }

    public static void processGameChoice(int option) {
        switch (option) {
            case 1:
                System.out.print("");
            case 2:
                System.out.print("");
            case 3:
                System.out.print("");
            case 4:
                System.out.print("");
            case 5:
                System.out.print("");
            case 6:
                System.out.print("");
            case 7:
                System.out.print("");
            default:
                System.out.print("Invalid Option");



        }
    }

}
