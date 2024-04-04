import exception.ResponseException;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

import chess.*;

import static ui.EscapeSequences.*;

public class ultConsoleGame {

    private static String EMPTY = "  ";

    public static Boolean reversed = false;

    public static Boolean print_white = true;

    private static final int SQUARE_SIZE_IN_CHARS = 1;


    public static void start(String color) throws ResponseException {
        if (color.equalsIgnoreCase("BLACK")){
            reversed = true;
        }
        System.out.println("Type 'start' to start game");
        String option = ConsolePostLogin.scanner.nextLine();
        String[] parts = option.split("\\s+");
        main(parts, color);
        System.out.println("In Game Menu");
        ConsolePostLogin.gameDisplay();
    }

    public static void main(String[] args, String color) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        out.print(ERASE_SCREEN);

        drawHeaders(out);
        drawChessBoard(out);



    }


    public static void drawHeaders(PrintStream out) {

        String[] letter_headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        if (reversed){
            Collections.reverse(Arrays.asList(letter_headers));
        }
        drawHeader(out, " ");
        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            drawHeader(out, letter_headers[boardCol]);
        }
        setDarkGrey(out);
        out.println();
    }

    public static void drawHeader(PrintStream out, String headerText) {
        int prefixLength;
        prefixLength = 1;
        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
    }


    public static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_GREEN);
        out.print(player);
        setDarkGrey(out);
    }
    public static void setDarkGrey(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }

    private static void drawChessBoard(PrintStream out) {
        for (int boardRow = 0; boardRow < 8; ++boardRow){
            colNumbers(out, boardRow);
            for (int boardCol = 0; boardCol < 8; ++boardCol) {
                printSquare(out);
            }
            setDarkGrey(out);
            out.println();
            print_white = !print_white;
        }
    }

    private static void printSquare(PrintStream out){
        if (print_white) {
            out.print(SET_BG_COLOR_GREEN);
            setGreen(out);
            print_white = false;
        }
        else {
            out.print(SET_BG_COLOR_BLACK);
            setBlack(out);
            print_white = true;
        }
        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));

    }

    private static void colNumbers(PrintStream out, int boardRow){
        String number;
        if (reversed) {
            number = String.valueOf(8- boardRow);
        }
        else {
            number = String.valueOf(boardRow + 1);
        }
        drawHeader(out, number);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGreen(PrintStream out) {
        out.print(SET_BG_COLOR_GREEN);
        out.print(SET_TEXT_COLOR_GREEN);
    }

}
