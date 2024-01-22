package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    int row;
    int col;

    int temp_row;
    int temp_col;


    public ChessPosition(int row, int col) {
        this.row = row -1;
        this.col = col -1;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
//        throw new RuntimeException("Not implemented");
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
//        throw new RuntimeException("Not implemented");
        return col;
    }
}
