import exception.ResponseException;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static ui.EscapeSequences.*;

public class ConsoleGame {
    private static final int BOARD_SIZE_IN_SQUARES = 4;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "  ";
    private static final String PAWN = " P ";
    private static final String ROOK = " R ";
    private static final String BISHOP = " B ";
    private static final String KNIGHT = " N ";
    private static final String QUEEN = " Q ";
    private static final String KING = " K ";

    private static final String[] funPieces = {"R", "N", "B", "K", "Q", "B", "N", "R"};

    private static final String[] pawns = {"P", "P", "P", "P", "P", "P", "P", "P" };
    private static final String[] numbersList = {"1", "2", "3", "4", "5", "6", "7", "8"};
    private static int counter = 0;
    private static Boolean reversed = true;

    public static void start() throws ResponseException {
        System.out.println("Type 'start' to start game, else type 'quit'");
        String option = ConsolePostLogin.scanner.nextLine();
        String[] parts = option.split("\\s+");
        if (parts[0].toLowerCase().equals("quit")){
            ConsolePostLogin.gameDisplay();
        }
        else{
            main(parts);
        }
    }




    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawChessBoard(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);

        drawHeaders(out);
        reversed = false;
        drawChessBoard(out);
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {
        setBlack(out);

        String[] letter_headers = {" ", "a", "b", "c", "d", "e", "f", "g", "h"};
        for (int boardCol = 0; boardCol < 9; ++boardCol) {
//            drawVerticalHeader(out, number_headers[boardCol]);
            drawHeader(out, letter_headers[boardCol]);
        }

//        String[] number_headers = {"1", "2", "3", "4", "5", "6", "7", "8"};
//        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//            drawHeader(out, number_headers[boardCol]);
//        }
        setDarkGrey(out);
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int suffixLength;
        int prefixLength;
//        suffixLength = 1;
        prefixLength = 1;
//        if (number/2 == 0) {
//            suffixLength = 2;
//            prefixLength = 2;
//        }
//        else{
//            suffixLength = 2;
//            prefixLength = 2;
//        }
        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
//        out.print(EMPTY.repeat(suffixLength));

    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setDarkGrey(out);
    }

    private static void drawChessBoard(PrintStream out) {
        if (reversed){
        createHalfBoard(out, false, false);
        createHalfBoard(out, true, true);}
        else{
            createHalfBoard(out, true, false);
            createHalfBoard(out, false, true);
        }

    }

    public static void createHalfBoard(PrintStream out, Boolean count, Boolean bottom){
        Boolean off = true;

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            if (count) {
                String number = String.valueOf(boardRow+5);
                drawHeader(out, number);
            }
            else {
                String number = String.valueOf(boardRow+1);
                drawHeader(out, number);

            }

            drawRowOfSquares(out, off, boardRow, bottom);

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                off = !off;
            }
        }
        counter = 0;
    }

    private static void checkPlayer(int index, PrintStream out, Boolean bottom) {
        if (reversed) {
            if (bottom) {
                if (index == 3) {
                    printWhitePlayer(out, funPieces[counter]);
                    counter += 1;
                } else if (index == 2) {
                    printWhitePlayer(out, "P");
                } else {
                    printWhitePlayer(out, " ");
                }
            } else {
                if (index == 0) {
                    printBlackPlayer(out, funPieces[counter]);
                    counter += 1;
                } else if (index == 1) {
                    printBlackPlayer(out, "P");
                } else {
                    printBlackPlayer(out, " ");
                }
            }
        }
        else{
            if (bottom) {
                if (index == 3) {
                    printBlackPlayer(out, funPieces[counter]);
                    counter += 1;
                } else if (index == 2) {
                    printBlackPlayer(out, "P");
                } else {
                    printBlackPlayer(out, " ");
                }
            } else {
                if (index == 0) {
                    printWhitePlayer(out, funPieces[counter]);
                    counter += 1;
                } else if (index == 1) {
                    printWhitePlayer(out, "P");
                } else {
                    printWhitePlayer(out, " ");
                }
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out, Boolean off, int row, Boolean bottom) {

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            if (!(squareRow == 0)) {
                drawHeader(out, " ");
            }
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
//                    setBlack(out);
//                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
//                    setWhite(out);
//                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
//                }
//                else {
//                    setWhite(out);
//                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
//                    setBlack(out);
//                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
//                }
                if (off) {
                    out.print(SET_BG_COLOR_GREEN);
                    checkPlayer(row, out, bottom);
                    setWhite(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    out.print(SET_BG_COLOR_BLACK);
                    checkPlayer(row, out, bottom);
                    setBlack(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                } else {
                    out.print(SET_BG_COLOR_BLACK);
                    checkPlayer(row, out, bottom);
                    setBlack(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    out.print(SET_BG_COLOR_GREEN);
                    checkPlayer(row, out, bottom);
                    setWhite(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                }
            }
        }
            setDarkGrey(out);
            out.println();
    }

    private static void drawVerticalLine(PrintStream out) {

        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_CHARS +
                (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_CHARS;

        for (int lineRow = 0; lineRow < LINE_WIDTH_IN_CHARS; ++lineRow) {
            setRed(out);
            out.print(EMPTY.repeat(boardSizeInSpaces));

            setBlack(out);
            out.println();
        }
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_GREEN);
        out.print(SET_TEXT_COLOR_GREEN);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setDarkGrey(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }

    private static void printBlackPlayer(PrintStream out, String player) {
        out.print(SET_TEXT_COLOR_RED);

        out.print(player);

        setWhite(out);
    }

    private static void printWhitePlayer(PrintStream out, String player) {
        out.print(SET_TEXT_COLOR_BLUE);

        out.print(player);

        setBlack(out);
    }
}
