package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor teamTurn;
    ChessBoard curr_board;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(curr_board, chessGame.curr_board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, curr_board);
    }

    public ChessGame() {
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
//        throw new RuntimeException("Not implemented");
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
//        throw new RuntimeException("Not implemented");
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }


    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
//        throw new RuntimeException("Not implemented");
        if (curr_board.getPiece(startPosition) != null){
            return curr_board.getPiece(startPosition).pieceMoves(curr_board, startPosition);
        }
        else{
            return null;
        }

    }

    public void changeTeam(){
        if (teamTurn == TeamColor.WHITE){
            teamTurn = TeamColor.BLACK;
        }
        else {
            teamTurn = TeamColor.WHITE;
        }
    }

    public boolean isCurrTeam(ChessMove move){
        if (curr_board.getPiece(move.startPosition).pieceColor == teamTurn){
            return true;
        }
        return false;
    }

    public ChessPosition findKing(TeamColor teamColor){
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = curr_board.getPiece(position);
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    return position;
                }
            }
        }
        return null;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
//        throw new RuntimeException("Not implemented");

            if (validMoves(move.startPosition).contains(move) && isCurrTeam(move) && !isInCheck(curr_board.getPiece(move.startPosition).pieceColor)){
                curr_board.addPiece(move.getEndPosition(), curr_board.getPiece(move.startPosition));
                curr_board.addPiece(move.getStartPosition(), null);
                changeTeam();
            }
            else {
                throw new InvalidMoveException();
            }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
//        throw new RuntimeException("Not implemented");
//        is the team currently in check?
//        include the position of the king
//        check all the opposing team

        ChessPosition king_position = findKing(teamColor);
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = curr_board.getPiece(position);
                ChessMove king_move = new ChessMove(position, king_position, null);
                if (piece == null){
                    continue;
                }
                if (piece.getTeamColor() == teamColor) {
                    continue;
                }
                if (validMoves(position).contains(king_move)) {
                    return true;

                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        boolean temp = false;
        int check = 0;
        ChessPosition king_position = findKing(teamColor);
        Collection<ChessMove> king_moves = validMoves(king_position);
        for (ChessMove king_move: king_moves){
            curr_board.addPiece(king_move.getEndPosition(), curr_board.getPiece(king_position));
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    ChessPosition position = new ChessPosition(i, j);
                    ChessMove possible_king_move = new ChessMove(position, king_move.endPosition, null);
                    ChessPiece piece = curr_board.getPiece(position);
                    if (piece == null){
                        continue;
                    }
                    if ((piece.getPieceType() == ChessPiece.PieceType.PAWN) && (i == 1 || i == 7)){
                        possible_king_move = new ChessMove(position, king_move.endPosition, ChessPiece.PieceType.QUEEN);
                    }
                    if (validMoves(position).contains(possible_king_move)){
                        check++;
                        temp = true;
                        break;
                    }
                }
                if (temp) {
                    temp = false;
                    break;
                }
            }
            curr_board.addPiece(king_move.getEndPosition(), null);

        }
    if (check == king_moves.size()) {
        return true;
    }
    return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
//        throw new RuntimeException("Not implemented");
        if (teamColor == TeamColor.WHITE){
            return isInCheckmate(TeamColor.WHITE);
        }
        return isInCheckmate(TeamColor.BLACK);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
//        throw new RuntimeException("Not implemented");
        curr_board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
//        throw new RuntimeException("Not implemented");
        return curr_board;
    }
}
