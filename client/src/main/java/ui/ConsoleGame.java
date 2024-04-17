package ui;

import exception.ResponseException;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

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

//    private static ChessBoard board = new ChessBoard();

    private static String team;

    public static ServerClient serverClient;

    public static String id;

    public static Collection<ChessPosition> positions = new HashSet<>() {
    };
    public static ChessPosition startPosition = null;

    public static ChessGame chessGame = new ChessGame();



    public static void start(ServerClient server, String gameID, String color, Boolean allowed) throws ResponseException {
        serverClient = server;
        if (color.equalsIgnoreCase("BLACK")) {
            reversed = true;
            team = "BLACK";
        }
        else{
        team = "WHITE";}
        id = gameID;
//        board.resetBoard();
//        chessGame.setBoard(board);
//        printGame(serverClient.getChessGame().getBoard());
        if (allowed){
        displayOptions();}
        else{
            displayObserveOptions();
        }
    }

    public static void displayObserveOptions() throws ResponseException {
        System.out.println("Type 'help' to see game options");
        String option = ConsolePostLogin.scanner.nextLine();
        String[] parts = option.split("\\s+");
        processObserveAction(parts);
    }


    public static void displayOptions() throws ResponseException {
            System.out.println("Type 'help' to see game options");
            String option = ConsolePostLogin.scanner.nextLine();
            String[] parts = option.split("\\s+");
            processGameAction(parts);
        }

    public static void processObserveAction(String[] option) throws ResponseException {
        switch (option[0].toLowerCase()) {
            case "redraw":
                printGame(serverClient.getChessGame().getBoard());
                displayOptions();
                break;
            case "leave":
                serverClient.leave(id);
                ConsolePostLogin.start(serverClient);
                break;
            case "help":
                System.out.println(serverClient.help("O"));
                displayOptions();
                break;
            default:
                System.out.print("Invalid Option");
                displayOptions();
                break;
        }}

    public static void processGameAction(String[] option) throws ResponseException {
        switch (option[0].toLowerCase()) {
            case "redraw":
                printGame(serverClient.getChessGame().getBoard());
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
                serverClient.resign(id);
                ConsolePostLogin.gameDisplay();
                break;
            case "help":
                System.out.println(serverClient.help(""));
                displayOptions();
                break;
            default:
                System.out.print("Invalid Option");
                displayOptions();
                break;
        }
    }

    public static void makeDisplay() throws ResponseException {
        ChessPosition tempPosition;
        System.out.println("Chess Piece Starting Position: ");
        String startOption = ConsolePostLogin.scanner.nextLine();
        String[] startParts = startOption.split("\\s+");
        if (startParts.length < 2){
            System.out.println("Select Chess Piece position <letter> <number>");
            makeDisplay();
        }
        if (!reversed) {
            int startCol = colInt(startParts[0]);
            tempPosition = new ChessPosition(Integer.parseInt(startParts[1]), startCol);
        }
        else{
            int startCol = colInt(startParts[0]);
            tempPosition = new ChessPosition(Integer.parseInt( startParts[1]), startCol);
        }
        ChessPiece chessPiece = serverClient.getChessGame().getBoard().getPiece(tempPosition);
        if (chessPiece == null){
            System.out.println("No piece present");
            makeDisplay();
        }
        viewPossibleMoves(tempPosition);
        System.out.println("Ending position: ");
        String endOption = ConsolePostLogin.scanner.nextLine();
        String[] endParts = endOption.split("\\s+");
        if (endParts[0].equalsIgnoreCase("none")){
            makeDisplay();
        }
        if (endParts.length < 2){
            System.out.println("Select Valid Move <letter> <number> or <empty>");
            makeDisplay();
        }
        int endCol = colInt(endParts[0]);
        ChessPosition endPosition = new ChessPosition(Integer.parseInt(endParts[1]), endCol);
        serverClient.makeMove(id, tempPosition,endPosition, chessPiece.getPieceType());
        displayOptions();
    }

    public static int colInt(String letter){
        switch (letter.toLowerCase()){
            case "a":
                return 1;
            case "b":
                return 2;
            case "c":
                return 3;
            case "d":
                return 4;
            case "e":
                return 5;
            case "f":
                return 6;
            case "g":
                return 7;
            case "h":
                return 8;
        }
        return 0;
    }

    public static void viewPossibleMoves(ChessPosition position){
        Collection<ChessMove> moves = serverClient.chessGame.validMoves(position);
        getAllPositions(moves);
        startPosition = position;
        printGame(serverClient.getChessGame().getBoard());
        positions = new HashSet<>();
        startPosition = null;

    }

    public static void getAllPositions(Collection<ChessMove> moves) {
        for (ChessMove move : moves) {
            ChessPosition possibleMove = move.getEndPosition();
            positions.add(possibleMove);
        }
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
            int row =  colNumbers(out, boardRow);
            for (int boardCol = 0; boardCol < 8; ++boardCol) {
                if (!(positions.isEmpty())) {
                    ChessPosition testPosition;
                    if (!reversed){
                    testPosition = new ChessPosition(row, boardCol+1);}
                    else{
                        testPosition = new ChessPosition(row, 8-boardCol);}
                    if (positions.contains(testPosition)) {
                        printSquare(out, boardRow, boardCol, "YES");
                    }
                    else if ((testPosition.getRow() == startPosition.getRow()) && (testPosition.getColumn() == startPosition.getColumn())){
                        printSquare(out, boardRow, boardCol, "NO");
                    }
                    else {
                        printSquare(out, boardRow, boardCol, "");
                    }
                }
                else {
                    printSquare(out, boardRow, boardCol, "");
                }
            }
            setDarkGrey(out);
            out.println();
            printWhite = !printWhite;
        }
    }

    private static void printSquare(PrintStream out, int row, int col, String option){
        if (option.equals("YES")){
            if (printWhite){
            out.print(SET_BG_COLOR_YELLOW);
            checkPiece(out, row, col);
            setYellow(out);
            printWhite = false;}
            else {
                out.print(SET_BG_COLOR_MAGENTA);
                checkPiece(out, row, col);
                setMagenta(out);
                printWhite = true;
            }
        }
        else if (option.equals("NO")){
            out.print(SET_BG_COLOR_LIGHT_GREY);
            checkPiece(out, row, col);
            setLightGrey(out);
            printWhite = !printWhite;
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
            piece = serverClient.getChessGame().getBoard().getPiece(new ChessPosition(8-row, col+1));

        }
        else {
            piece = serverClient.getChessGame().getBoard().getPiece(new ChessPosition(row + 1, 8 - col));
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

    private static int colNumbers(PrintStream out, int boardRow){
        String number;
        int row;
        if (!reversed) {
            number = String.valueOf(8- boardRow);
            row =  8 -boardRow;
        }
        else {
            number = String.valueOf(boardRow + 1);
            row =  boardRow + 1;
        }
        drawHeader(out, number);
        return row;
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGreen(PrintStream out) {
        out.print(SET_BG_COLOR_GREEN);
        out.print(SET_TEXT_COLOR_GREEN);
    }

    private static void setYellow(PrintStream out) {
        out.print(SET_BG_COLOR_YELLOW);
        out.print(SET_TEXT_COLOR_YELLOW);
    }

    private static void setMagenta(PrintStream out) {
        out.print(SET_BG_COLOR_MAGENTA);
        out.print(SET_TEXT_COLOR_MAGENTA);
    }

    private static void setLightGrey(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
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
