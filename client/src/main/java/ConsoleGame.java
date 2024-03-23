import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ConsoleGame {
    private static final int BOARD_SIZE_IN_SQUARES = 4;
    private static final int SQUARE_SIZE_IN_CHARS = 4;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";
    private static final String PAWN = " P ";
    private static final String ROOK = " R ";
    private static final String BISHOP = " B ";
    private static final String KNIGHT = " N ";
    private static final String QUEEN = " Q ";
    private static final String KING = " K ";

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawChessBoard(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {
        setBlack(out);

        String[] letter_headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] number_headers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int boardCol = 0; boardCol < 8; ++boardCol) {
//            drawVerticalHeader(out, number_headers[boardCol]);
            drawHeader(out, letter_headers[boardCol], boardCol);
        }

//        String[] number_headers = {"1", "2", "3", "4", "5", "6", "7", "8"};
//        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//            drawHeader(out, number_headers[boardCol]);
//        }
        setDarkGrey(out);
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText, int number) {
        int suffixLength;
        int prefixLength;
        if (number/2 == 0) {
            suffixLength = 2;
            prefixLength = 2;
        }
        else{
            suffixLength = 2;
            prefixLength = 2;
        }
        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));

    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out) {
        Boolean off = true;

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            drawRowOfSquares(out, off);

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                off = !off;
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out, Boolean off) {

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
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
                    setWhite(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    setBlack(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                }
                else {
                    setBlack(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    setWhite(out);
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                }
            }

            setDarkGrey(out);
            out.println();
        }
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
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
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

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setWhite(out);
    }
}
