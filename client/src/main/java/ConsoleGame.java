import exception.ResponseException;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import chess.*;
import ui.ServerClient;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;

import static ui.EscapeSequences.*;
import static webSocketMessages.serverMessages.ServerMessage.ServerMessageType.LOAD_GAME;

public class ConsoleGame {

    private static final String EMPTY = "  ";

    public static Boolean reversed = false;

    public static Boolean printWhite = true;

    private static final int SQUARE_SIZE_IN_CHARS = 1;

    private static ChessBoard board = new ChessBoard();

    private static String team;

    public static ServerClient serverClient;

    public static String id;

    public static Collection<ChessMove> moves;
    public static ChessPosition startPosition;



    public static void start(ServerClient server, String gameID, String color) throws ResponseException {
        serverClient = server;
        if (color.equalsIgnoreCase("BLACK")) {
            reversed = true;
            team = "BLACK";
        }
        else{
        team = "WHITE";}
        id = gameID;
//        board = serverClient.getChessGame().getBoard();
//        printGame(board);
//        serverClient.processNotification();
        displayOptions();

    }

    public static void displayOptions() throws ResponseException {
//        board = serverClient.getChessGame().getBoard();
//        printGame(board);
        System.out.println(serverClient.getNotification().toString());
//            processNotification(serverClient.getNotification());
            System.out.println("Type 'help' to see game options");
            String option = ConsolePostLogin.scanner.nextLine();
            String[] parts = option.split("\\s+");
            processGameAction(parts);

        }

//    public static void processNotification(Notification notification){
//        System.out.println(notification.getMessage());
//        if (notification.getServerMessageType() == LOAD_GAME){
//            LoadGame loadGame = notification.getServerMessageType();
//        }
//    }


    public static void processGameAction(String[] option) throws ResponseException {
        switch (option[0].toLowerCase()) {
            case "redraw":
                printGame(board);
                displayOptions();
                break;
            case "leave":
                serverClient.leave(id);
                ConsolePostLogin.start(serverClient);
                break;
            case "make":
                makeDisplay();
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

    public static void makeDisplay() throws ResponseException {
        System.out.println("Chess Piece Starting Position: ");
        String startOption = ConsolePostLogin.scanner.nextLine();
        String[] startParts = startOption.split("\\s+");

        if (startParts.length < 2){
            System.out.println("Select Chess Piece position <row> <col>");
            makeDisplay();
        }
        ChessPosition startPosition = new ChessPosition(Integer.parseInt(startParts[0]), Integer.parseInt(startParts[1]));
        ChessPiece chessPiece = board.getPiece(startPosition);
        viewPossibleMoves(startPosition);
        System.out.println("Ending position: ");
        String endOption = ConsolePostLogin.scanner.nextLine();
        String[] endParts = endOption.split("\\s+");
        if (endParts.length < 2){
            System.out.println("Select Valid Move <row> <col>");
        }
        ChessPosition endPosition = new ChessPosition(Integer.parseInt(endParts[0]), Integer.parseInt(endParts[1]));
        serverClient.makeMove(startPosition,endPosition, chessPiece.getPieceType());
        displayOptions();
    }

    public static void viewPossibleMoves(ChessPosition position){
        ChessGame chessGameMoves = new ChessGame();
        moves = chessGameMoves.validMoves(position);
        startPosition = position;
        printGame(board);
        moves = null;
        startPosition = null;

    }

    public static void printGame(ChessBoard board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawHeaders(out);
        drawChessBoard(out);
        printWhiteText(out);
    }


    public static void drawHeaders(PrintStream out) {

        String[] letterHeaders = {"a", "b", "c", "d", "e", "f", "g", "h"};
        if (reversed){
            Collections.reverse(Arrays.asList(letterHeaders));
        }
        drawHeader(out, " ");
        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            drawHeader(out, letterHeaders[boardCol]);
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
                if (!(moves == null)){
                for (ChessMove move : moves){
                    ChessPosition possibleMove = move.getEndPosition();
                    int possibleRow = possibleMove.getRow();
                    int possibleCol = possibleMove.getColumn();
                    if ((possibleRow == boardRow) && (possibleCol == boardCol)){
                        printSquare(out, boardRow, boardCol, true);
                    }
                    else if ((possibleRow == startPosition.getRow()) && (possibleCol == startPosition.getColumn())){
                        printSquare(out, boardRow, boardCol, false);
                    }
                    else {
                        printSquare(out, boardRow, boardCol, true);
                    }
                }}
                else {
                    printSquare(out, boardRow, boardCol, true);
                }
            }
            setDarkGrey(out);
            out.println();
            printWhite = !printWhite;
        }
    }

    private static void printSquare(PrintStream out, int row, int col, Boolean option){
        if (option){
            out.print(SET_BG_COLOR_YELLOW);
            checkPiece(out, row, col);
            setGreen(out);
            printWhite = false;
        }
        else if (!option){
            out.print(SET_BG_COLOR_LIGHT_GREY);
            checkPiece(out, row, col);
            setGreen(out);
            printWhite = false;
        }
        else if (printWhite) {
            out.print(SET_BG_COLOR_GREEN);
            checkPiece(out, row, col);
            setGreen(out);
            printWhite = false;
        }
        else {
            out.print(SET_BG_COLOR_BLACK);
            checkPiece(out, row, col);
            setBlack(out);
            printWhite = true;
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

    private static void printWhiteText(PrintStream out){
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void printBluePlayer(PrintStream out, String player) {
        out.print(SET_TEXT_COLOR_BLUE);
        out.print(player);
        setBlack(out);
    }

}
