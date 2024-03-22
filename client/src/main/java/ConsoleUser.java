import java.util.Scanner;
import ui.*;

public class ConsoleUser {
    static Scanner scanner = new Scanner(System.in);


    public static void loginDisplay() {
        System.out.printf("%d. %s%n", 1, "register" );
        System.out.printf("%d. %s%n", 2, "login" );
        System.out.printf("%d. %s%n", 3, "quit" );
        System.out.printf("%d. %s%n", 4, "help" );
        System.out.print("Option: ");
        int option = scanner.nextInt();
        processLoginChoice(option);

    }

    public static void processLoginChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.print("");
            case 2:
                System.out.print("");
            case 3:
                System.out.print("");
            case 4:
                System.out.print("");
            default:
                System.out.println("Invalid choice");

        }

    }
}
