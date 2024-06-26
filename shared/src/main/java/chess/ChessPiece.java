package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    Collection<ChessMove> moves;
    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;

    int row;

    int col;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return Objects.equals(moves, that.moves) && pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(moves, pieceColor, type);
    }

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
//        throw new RuntimeException("Not implemented");
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
//        throw new RuntimeException("Not implemented");
        return type;
    }

    public boolean isValid (int row, int col){
        if ((row < 1) || (row > 8)){
            return false;
        }
        if ((col < 1) || (col > 8)){
            return false;
        }
        return true;
    }

    public boolean isEmpty(ChessBoard board, ChessPosition position){
        if ((board.getPiece(position)) == null){
            return true;
        }
        return false;
    }

    public boolean isTeam(ChessBoard board, ChessPosition position){
        if (board.getPiece(position).getTeamColor() == pieceColor){
            return true;
        }
        return false;
    }

    public void promotion(ChessPosition myPosition, ChessPosition position, Collection<ChessMove> moves){
        moves.add(new ChessMove(myPosition, position, PieceType.QUEEN));
        moves.add(new ChessMove(myPosition, position, PieceType.BISHOP));
        moves.add(new ChessMove(myPosition, position, PieceType.KNIGHT));
        moves.add(new ChessMove(myPosition, position, PieceType.ROOK));
    }

    public boolean isPromotion(ChessPosition myPosition, ChessPosition position, Collection<ChessMove> moves){
        if ((position.getRow() == 8) || position.getRow() == 1){
            return true;
        }
        return false;

    }

    public void diagonalPawnMove(ChessBoard board, ChessPosition myPosition, ChessPosition position, Collection<ChessMove> moves){
        if (!isEmpty(board, position)){
            if (!isTeam(board, position)){
                if (isPromotion(myPosition, position, moves)){
                    promotion(myPosition, position, moves);
                } else {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }
    }

    public void pawnMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){
        row = myPosition.getRow();
        col = myPosition.getColumn();

        // White
        if (pieceColor == ChessGame.TeamColor.WHITE){
            //Up
            ChessPosition position = new ChessPosition(row+1,col);
            if (isEmpty(board,position)) {
                if (isPromotion(myPosition, position, moves)){
                    promotion(myPosition, position, moves);
                }
                else if (row == 2) {
                    ChessPosition tempPosition = new ChessPosition(row + 2, col);
                    if (isEmpty(board, tempPosition)) {
                        moves.add(new ChessMove(myPosition, position, null));
                        moves.add(new ChessMove(myPosition, tempPosition, null));
                    }
                    else {
                        moves.add(new ChessMove(myPosition, position, null));
                    }
                }
                else{
                    moves.add(new ChessMove(myPosition, position, null));
                }

            }



            // Diagonal Right
            if (isValid(row+1, col+1)){
                position = new ChessPosition(row+1, col+1);
                diagonalPawnMove(board, myPosition, position, moves);
            }

            // Diagonal Left
            if (isValid(row+1, col-1)){
                position = new ChessPosition(row+1, col-1);
                diagonalPawnMove(board, myPosition, position, moves);
            }
        }

        // Black
        if (pieceColor == ChessGame.TeamColor.BLACK){
            //Up
            ChessPosition position = new ChessPosition(row-1,col);
            if (isEmpty(board,position)) {
                if (isPromotion(myPosition, position, moves)){
                    promotion(myPosition, position, moves);
                }
                else if (row == 7) {
                    ChessPosition tempPosition = new ChessPosition(row - 2, col);
                    if (isEmpty(board, tempPosition)) {
                        moves.add(new ChessMove(myPosition, position, null));
                        moves.add(new ChessMove(myPosition, tempPosition, null));
                    }
                    else{
                        moves.add(new ChessMove(myPosition, position, null));

                    }
                }
                else{
                    moves.add(new ChessMove(myPosition, position, null));
                }

            }



            // Diagonal Left
            if (isValid(row-1, col-1)){
                position = new ChessPosition(row-1, col-1);
                diagonalPawnMove(board, myPosition, position, moves);
            }

            // Diagonal Right
            if (isValid(row-1, col+1)){
                position = new ChessPosition(row-1, col+1);
                diagonalPawnMove(board, myPosition, position, moves);
            }
        }
    }

    public Boolean runLongMoves(ChessBoard board, ChessPosition myPosition, int row, int col, Collection<ChessMove> moves){
        if (!isValid(row,col)){
            return false;
        }
        ChessPosition position = new ChessPosition(row, col);
        if (!isEmpty(board, position)){
            if (isTeam(board, position)){
                return false;
            }
            else{
                moves.add(new ChessMove(myPosition, position, null));
                return false;
            }
        }
        moves.add(new ChessMove(myPosition, position, null));
        return true;
    }

    public void rookMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){

        // Up
        for (int i=1; i<9; i++){
            row = myPosition.getRow() + i;
            col = myPosition.getColumn();
            if (!(runLongMoves(board, myPosition, row, col, moves))){
                break;
            }
        }

        // Down
        for (int i=1; i<9; i++){
            row = myPosition.getRow() - i;
            col = myPosition.getColumn();
            if (!(runLongMoves(board, myPosition, row, col, moves))){
                break;
            }        }

        // Right
        for (int i=1; i<9; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn() + i;
            if (!(runLongMoves(board, myPosition, row, col, moves))){
                break;
            }        }

        // Left
        for (int i=1; i<9; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn() - i;
            if (!(runLongMoves(board, myPosition, row, col, moves))){
                break;
            }        }
    }

    public void kMoves(ChessBoard board, ChessPosition myPosition, int row, int col, Collection<ChessMove> moves){
        if (!isValid(row,col)){
            return;
        }
        ChessPosition position = new ChessPosition(row, col);
        if (!isEmpty(board,position)) {
            if (isTeam(board, position)){
                return;
            }
            else{
                moves.add(new ChessMove(myPosition, position, null));
                return;
            }
        }
        moves.add(new ChessMove(myPosition,position, null));
    }

    public void knightMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        row = myPosition.getRow();
        col = myPosition.getColumn();
        int[][] spots = {{row + 2, col - 1}, {row + 2, col + 1}, {row - 1, col + 2}, {row + 1, col + 2}, {row - 2, col + 1}, {row - 2, col - 1}, {row - 1, col - 2}, {row + 1, col - 2}};
        for (int i = 0; i < spots.length; i++) {
            row = spots[i][0];
            col = spots[i][1];
            kMoves(board, myPosition, row, col, moves);
        }
    }


    public void kingMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){
        row = myPosition.getRow();
        col = myPosition.getColumn();
        int[][] spots = {{row+1, col}, {row+1, col+1},{row,col+1},{row-1, col-1},{row-1, col},{row-1,col+1},{row,col-1},{row+1,col-1}};
        for (int i=0; i < spots.length; i++){
            row = spots[i][0];
            col = spots[i][1];
            kMoves(board, myPosition, row, col, moves);
    }}

    public void bishopMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){

        // Diagonal Upper Right;
        for (int i=1; i<9; i++){
            row = myPosition.getRow() + i;
            col = myPosition.getColumn() + i;
            if (!(runLongMoves(board, myPosition, row, col, moves))){
                break;
            }
        }

        // Diagonal Upper Left;
        for (int i=1; i<9; i++){
            row = myPosition.getRow() + i;
            col = myPosition.getColumn() - i;
            if (!(runLongMoves(board, myPosition, row, col, moves))){
                break;
            }
        }

        //Diagonal Lower Right;
        for (int i=1; i<9; i++){
            row = myPosition.getRow() - i;
            col = myPosition.getColumn() + i;
            if (!(runLongMoves(board, myPosition, row, col, moves))){
                break;
            }
        }

        //Diagonal Lower Left
        for (int i=1; i<9; i++){
            row = myPosition.getRow() - i;
            col = myPosition.getColumn() - i;
            if (!(runLongMoves(board, myPosition, row, col, moves))){
                break;
            }
    }}

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid move
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
//        throw new RuntimeException("Not implemented");
        Collection<ChessMove> moves = new HashSet<>();
        if (type == PieceType.BISHOP){
            bishopMoves(board, myPosition, moves);
        }
        if (type == PieceType.KING){
            kingMoves(board, myPosition, moves);
        }

        if (type == PieceType.KNIGHT){
            knightMoves(board, myPosition, moves);
        }

        if(type == PieceType.ROOK){
            rookMoves(board, myPosition, moves);
        }

        if (type == PieceType.QUEEN){
            bishopMoves(board, myPosition, moves);
            rookMoves(board, myPosition, moves);
        }

        if (type == PieceType.PAWN){
            pawnMoves(board, myPosition, moves);
        }

        return moves;
    }
}
