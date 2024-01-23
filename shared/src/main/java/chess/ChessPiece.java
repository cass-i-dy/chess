package chess;

import java.util.ArrayList;
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
    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
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
        return true;
    }

    public static boolean isTeam(ChessBoard board, ChessPosition position, ChessPosition myPosition){
        if (board.getPiece(position) == null) {
            return true;
        }
        if (board.getPiece(position).getTeamColor() == board.getPiece(myPosition).getTeamColor()){
            return true;
        }
        return false;
    }

    public Collection<ChessMove> queenMove(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){
        ChessPosition position;
        int row;
        int col;
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row+i, col);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Row Down
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row-i, col);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Col Right
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row, col+i);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Col Left
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row, col-i);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() - i;
            col = myPosition.getColumn() - i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Diagonal Moves to bottom right
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() - i;
            col = myPosition.getColumn() + i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Diagonal moves top left
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() + i;
            col = myPosition.getColumn() - i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Diagonal moves top right
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() + i;
            col = myPosition.getColumn() + i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null) {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
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
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Row Down
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row-i, col);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Col Right
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row, col+i);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Col Left
        for (int i = 1; i <= 8; i++){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            position = new ChessPosition(row, col-i);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        return moves;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves){
        int row;
        int col;
        ChessPosition position;

        row = myPosition.getRow();
        col = myPosition.getColumn();
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE ) {
            // Top Right
            position = new ChessPosition(row + 1, col + 1);
            if (isValid(board, position) && !isTeam(board, position, myPosition)){
                moves.add(new ChessMove(myPosition, position, null));

            }
            // Up
            position = new ChessPosition(row + 1, col);
            if (isValid(board, position)) {
                if (board.getPiece(position) == null) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }

            // Top Left
            position = new ChessPosition(row + 1, col - 1);
            if (isValid(board, position) && !isTeam(board, position, myPosition)){
                moves.add(new ChessMove(myPosition, position, null));

            }
        }

        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            // Top Right
            position = new ChessPosition(row - 1, col - 1);
            if (isValid(board, position) && !isTeam(board, position, myPosition)) {
                moves.add(new ChessMove(myPosition, position, null));

            }

            // Forward
            position = new ChessPosition(row - 1, col);
            if (isValid(board, position)) {
                if (board.getPiece(position) == null) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }

            // Top Left
            position = new ChessPosition(row - 1, col + 1);
            if (isValid(board, position) && !isTeam(board, position, myPosition)) {
                moves.add(new ChessMove(myPosition, position, null));

            }
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
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Tall Up Left
        position = new ChessPosition(row + 2, col - 1);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Short Right Down
        position = new ChessPosition(row -1, col + 2);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Short Right Up
        position = new ChessPosition(row +1, col + 2);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Tall down Right
        position = new ChessPosition(row - 2, col + 1);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Tall down Left
        position = new ChessPosition(row - 2, col - 1);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Short Left Down
        position = new ChessPosition(row -1, col - 2);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Short Left Up
        position = new ChessPosition(row + 1, col - 2);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
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
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Right
        position = new ChessPosition(row, col + 1);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Diagonal down right
        position = new ChessPosition(row - 1, col + 1);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // down
        position = new ChessPosition(row - 1, col );
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Diagonal down left
        position = new ChessPosition(row - 1, col - 1);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Left
        position = new ChessPosition(row, col - 1);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Diagonal left up
        position = new ChessPosition(row + 1, col - 1);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }

        // Up
        position = new ChessPosition(row + 1, col);
        if (isValid(board, position)) {
            if (board.getPiece(position) == null) {
                moves.add(new ChessMove(myPosition, position, null));
            } else {
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
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
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Diagonal Moves to bottom right
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() - i;
            col = myPosition.getColumn() + i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Diagonal moves top left
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() + i;
            col = myPosition.getColumn() - i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        // Diagonal moves top right
        for (int i = 1; i <= 9; i++) {
            row = myPosition.getRow() + i;
            col = myPosition.getColumn() + i;
            position = new ChessPosition(row, col);
            if (!isValid(board, position)) {
                break;
            }
            if (board.getPiece(position) != null){
                if (!isTeam(board, position, myPosition)) {
                    moves.add(new ChessMove(myPosition, position, null));
                    break;
                }
                break;
            }
            moves.add(new ChessMove(myPosition, position, null));
        }

        return moves;
    }



    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
//        throw new RuntimeException("Not implemented");
        Collection<ChessMove> moves = new HashSet<>();

        if (type == PieceType.BISHOP) {
            bishopMoves(board, myPosition, moves);
        }
        if (type == PieceType.KNIGHT) {
            knightMoves(board, myPosition, moves);
        }
        if (type == PieceType.ROOK) {
            rookMove(board, myPosition, moves);
        }
        if (type == PieceType.PAWN) {
            pawnMoves(board, myPosition, moves);
        }
        if (type == PieceType.QUEEN) {
            queenMove(board, myPosition, moves);
        }
        if (type == PieceType.KING) {
            kingMoves(board, myPosition, moves);
        }

        return moves;
    }
}
