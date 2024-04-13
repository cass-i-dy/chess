package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor teamTurn;
    ChessBoard currBoard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(currBoard, chessGame.currBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, currBoard);
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
        ChessPiece possiblePiece = null;
//        throw new RuntimeException("Not implemented");
        if (currBoard.getPiece(startPosition) != null){
            Collection<ChessMove> moves = currBoard.getPiece(startPosition).pieceMoves(currBoard, startPosition);
            Collection<ChessMove> finalMoves = new HashSet<>();
            for (ChessMove move: moves){
                if (currBoard.getPiece(move.getEndPosition()) != null){
                    possiblePiece = currBoard.getPiece(move.getEndPosition());
                }
                currBoard.addPiece(move.getEndPosition(), currBoard.getPiece(move.getStartPosition()));
                currBoard.addPiece(move.getStartPosition(),null);
                if (!isInCheck(currBoard.getPiece(move.getEndPosition()).getTeamColor())){
                    finalMoves.add(move);
                }
                currBoard.addPiece(move.getStartPosition(), currBoard.getPiece(move.getEndPosition()));
                currBoard.addPiece(move.getEndPosition(),possiblePiece);
                possiblePiece = null;
            }
            return finalMoves;
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
        if (currBoard.getPiece(move.startPosition).pieceColor == teamTurn){
            return true;
        }
        return false;
    }

    public ChessPosition findKing(TeamColor teamColor){
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = currBoard.getPiece(position);
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

            if (validMoves(move.startPosition).contains(move) && isCurrTeam(move)){
                currBoard.addPiece(move.getEndPosition(), currBoard.getPiece(move.startPosition));
                currBoard.addPiece(move.getStartPosition(), null);
                if (move.getPromotionPiece() != null){
                     currBoard.addPiece(move.getEndPosition(), new ChessPiece(currBoard.getPiece(move.getEndPosition()).getTeamColor(), move.getPromotionPiece()));
                }
                if (isInCheck(currBoard.getPiece(move.getEndPosition()).pieceColor)){
                    currBoard.addPiece(move.getStartPosition(), currBoard.getPiece(move.endPosition));
                    currBoard.addPiece(move.getEndPosition(), null);
                }
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

        ChessPosition kingPosition = findKing(teamColor);
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = currBoard.getPiece(position);
                ChessMove kingMove = new ChessMove(position, kingPosition, null);
                if (piece == null){
                    continue;
                }
                if (piece.getTeamColor() == teamColor) {
                    continue;
                }
                if (piece.pieceMoves(currBoard, position).contains(kingMove)) {
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
        ChessPosition kingPosition = findKing(teamColor);
        Collection<ChessMove> kingMoves = currBoard.getPiece(kingPosition).pieceMoves(currBoard, kingPosition);
        for (ChessMove kingMove: kingMoves){
            currBoard.addPiece(kingMove.getEndPosition(), currBoard.getPiece(kingPosition));
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    ChessPosition position = new ChessPosition(i, j);
                    ChessMove possibleKingMove = new ChessMove(position, kingMove.endPosition, null);
                    ChessPiece piece = currBoard.getPiece(position);
                    if (piece == null){
                        continue;
                    }
                    if (piece.getTeamColor() == teamColor){
                        continue;
                    }
                    if ((piece.getPieceType() == ChessPiece.PieceType.PAWN) && (i == 1 || i == 7)){
                        possibleKingMove = new ChessMove(position, kingMove.endPosition, ChessPiece.PieceType.QUEEN);
                    }
                    Collection<ChessMove> foo = piece.pieceMoves(currBoard, position);
                    if (piece.pieceMoves(currBoard, position).contains(possibleKingMove)){
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
            currBoard.addPiece(kingMove.getEndPosition(), null);

        }
    if (check == kingMoves.size() && check != 0) {
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
        currBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
//        throw new RuntimeException("Not implemented");
        return currBoard;
    }
}
