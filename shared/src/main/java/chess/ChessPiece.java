package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;


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

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public static boolean isValid(ChessBoard board, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        if (row > 8 || row < 1) {
            return false;
        }
        if (col > 8 || col < 1) {
            return false;
        }
        if (board.getPiece(position) == null) {
            return false;
        }
        return true;
    }


    public Collection<ChessMove> queenMove(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){
        ChessPosition position;
        int row;
        int col;
        // Row Up
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row+i, col);
            if (!isValid(board, position)){
                break;
            }
            moves.add(position);
        }

        // Row Down
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row-i, col);
            if (!isValid(board, position)){
                break;
            }
            moves.add(position);
        }

        // Col Right
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row, col+i);
            if (!isValid(board, position)){
                break;
            }
            moves.add(position);
        }

        // Col Left
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row, col-i);
            if (!isValid(board, position)){
                break;
            }
            moves.add(position);
        }

        int row;
        int col;
        ChessPosition position;
        // Diagonal moves to bottom left
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() - i;
            col = myPosition.getColumn() - i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            moves.add(position);
        }

        // Diagonal Moves to bottom right
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() + i;
            col = myPosition.getColumn() - i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            moves.add(position);
        }

        // Diagonal moves top left
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() - i;
            col = myPosition.getColumn() + i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            moves.add(position);
        }

        // Diagonal moves top right
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() + i;
            col = myPosition.getColumn() + i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            moves.add(position);
        }

        return moves;
    }



    public Collection<ChessMove> rookMove(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){
        ChessPosition position;
        int row;
        int col;
        // Row Up
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row+i, col);
            if (!isValid(board, position)){
                break;
            }
            moves.add(position);
        }

        // Row Down
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row-i, col);
            if (!isValid(board, position)){
                break;
            }
            moves.add(position);
        }

        // Col Right
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row, col+i);
            if (!isValid(board, position)){
                break;
            }
            moves.add(position);
        }

        // Col Left
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row, col-i);
            if (!isValid(board, position)){
                break;
            }
            moves.add(position);
        }

        return moves;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){
        int row;
        int col;
        ChessPosition position;

        row = myPosition.getRow();
        col = myPosition.getColumn();

        // Top Right
        position = new ChessPosition(row + 1, col + 1);
        if (isValid(board, position)){
            moves.add(position);
        }
        // Up
        position = new ChessPosition(row + 1, col);
        if (isValid(board, position)){
            moves.add(position);
        }

        // Top Left
        position = new ChessPosition(row + 1, col - 1);
        if (isValid(board, position)){
            moves.add(position);
        }
        return moves;
    }


    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){
        int row;
        int col;
        ChessPosition position;

        row = myPosition.getRow();
        col = myPosition.getColumn();

        // Tall Up Right
        position = new ChessPosition(row + 2, col + 1);
        if (isValid(board, position)){
            moves.add(position);
        }

        // Tall Up Left
        position = new ChessPosition(row + 2, col - 1);
        if (isValid(board, position)){
            moves.add(position);
        }

        // Short Right Down
        position = new ChessPosition(row -1, col + 2);
        if (isValid(board, position)){
            moves.add(position);
        }

        // Short Right Up
        position = new ChessPosition(row +1, col + 2);
        if (isValid(board, position)){
            moves.add(position);
        }

        // Tall down Right
        position = new ChessPosition(row - 2, col + 1);
        if (isValid(board, position)){
            moves.add(position);
        }

        // Tall down Left
        position = new ChessPosition(row - 2, col - 1);
        if (isValid(board, position)){
            moves.add(position);
        }

        // Short Left Down
        position = new ChessPosition(row -1, col - 2);
        if (isValid(board, position)){
            moves.add(position);
        }

        // Short Left Up
        position = new ChessPosition(row + 1, col - 2);
        if (isValid(board, position)){
            moves.add(position);
        }







        return moves;
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){
        int row;
        int col;
        ChessPosition position;

        row = myPosition.getRow();
        col = myPosition.getColumn();

        // Diagonal up Right
        position = new ChessPosition(row + 1, col + 1);
        if (isValid(board, position)){
            moves.add(position);
        }

        // Right
        position = new ChessPosition(row, col + 1);
        if (isValid(board, position)){
            moves.add(position);
        }

        // Diagonal down right
        position = new ChessPosition(row - 1, col + 1);
        if (isValid(board, position)){
            moves.add(position);
        }

        // down
        position = new ChessPosition(row - 1, col );
        if (isValid(board, position)){
            moves.add(position);
        }

        // Diagonal down left
        position = new ChessPosition(row - 1, col - 1);
        if (isValid(board,  position)){
            moves.add(position);
        }

        // Left
        position = new ChessPosition(row + 1, col - 1);
        if (isValid(board,  position)){
            moves.add(position);
        }

        // Up
        position = new ChessPosition(row + 1, col);
        if (isValid(board,  position)){
            moves.add(position);
        }

        return moves;
    }


    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int row;
        int col;
        ChessPosition position;
        // Diagonal moves to bottom left
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() - i;
            col = myPosition.getColumn() - i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            moves.add(position);
        }

        // Diagonal Moves to bottom right
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() + i;
            col = myPosition.getColumn() - i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            moves.add(position);
        }

        // Diagonal moves top left
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() - i;
            col = myPosition.getColumn() + i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            moves.add(position);
        }

        // Diagonal moves top right
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() + i;
            col = myPosition.getColumn() + i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            moves.add(position);
        }

        return moves;
    }


//        int row = myPosition.getRow();
//        int col = myPosition.getColumn();
//        if (!isValid(board, myPosition, row, col)){
//            return moves;
//        }
//        moves.add
//        return bishopMoves(board, myPosition , row-1, col-1, moves)
//
//    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
//        throw new RuntimeException("Not implemented");
        Collection<ChessMove> moves = new ArrayList<>();
        bishopMoves(board, myPosition, moves);
        return moves;
    }
}
