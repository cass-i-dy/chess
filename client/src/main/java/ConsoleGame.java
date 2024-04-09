import exception.ResponseException;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

import chess.*;
import ui.ServerClient;

import static ui.EscapeSequences.*;

public class ConsoleGame {

    private static String EMPTY = "  ";

    public static Boolean reversed = false;

    public static Boolean print_white = true;

    private static final int SQUARE_SIZE_IN_CHARS = 1;

    private static ChessBoard board;

    private static String team;

    public static ServerClient serverClient;


    public static void start(ServerClient server, String color) throws ResponseException {
        serverClient = server;
        if (color.equalsIgnoreCase("BLACK")) {
            reversed = true;
            team = "BLACK";
        }
        else{
        team = "WHITE";}
    }

    public static void displayOptions() throws ResponseException {

            System.out.println("Type 'help' to see game options");
            String option = ConsolePostLogin.scanner.nextLine();
            String[] parts = option.split("\\s+");
            processGameAction(parts);
        }


    public static void processGameAction(String[] option) throws ResponseException {
        switch (option[0].toLowerCase()) {
            case "redraw":
                printGame(option, team);
                displayOptions();
                break;
            case "leave":
                ConsolePostLogin.gameDisplay();
                break;
            case "make":
                break;
            case "resign":
                ConsolePostLogin.gameDisplay();
                break;
            case "help":
                System.out.println(serverClient.help(""));
                displayOptions();
                break;
            default:
                System.out.print("Invalid Option");
        }
    }

    public static void printGame(String[] args, String color) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        board = new ChessBoard();
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
                printSquare(out, boardRow, boardCol);
            }
            setDarkGrey(out);
            out.println();
            print_white = !print_white;
        }
    }

    private static void printSquare(PrintStream out, int row, int col){
        if (print_white) {
            out.print(SET_BG_COLOR_GREEN);
            checkPiece(out, row, col);
            setGreen(out);
            print_white = false;

        }
        else {
            out.print(SET_BG_COLOR_BLACK);
            checkPiece(out, row, col);
            setBlack(out);
            print_white = true;
        }
        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
    }

    private static void checkPiece(PrintStream out, int row, int col){
        ChessPiece piece;
        if (!reversed){
            piece = board.getPiece(new ChessPosition(8-row, 8-col));

        }
        else {
            piece = board.getPiece(new ChessPosition(row + 1, col + 1));
        }
        if (piece == null) {
            printRedPlayer(out, " ");
            return;
        }
        ChessPiece.PieceType type = piece.getPieceType();
        String stringType = getStringType(type.name());
        ChessGame.TeamColor color = piece.getTeamColor();
        if (color == ChessGame.TeamColor.WHITE) {
            printBluePlayer(out, stringType);
        }
        if (color == ChessGame.TeamColor.BLACK) {
            printRedPlayer(out, stringType);
        }
    }

    private static String getStringType(String type){
        if (type.equals("KING")) {
            return "K";
        }
        if (type.equals("QUEEN")) {
            return "Q";
        }
        if (type.equals("KNIGHT")) {
            return "N";
        }
        if (type.equals("BISHOP")) {
            return "B";
        }
        if (type.equals("ROOK")) {
            return "R";
        }
        if (type.equals("PAWN")) {
            return "P";
        }
        return null;
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

    private static void printRedPlayer(PrintStream out, String player) {
        out.print(SET_TEXT_COLOR_RED);
        out.print(player);
        setGreen(out);
    }

    private static void printBluePlayer(PrintStream out, String player) {
        out.print(SET_TEXT_COLOR_BLUE);
        out.print(player);
        setBlack(out);
    }

}
