import chess.ChessGame;
import chess.ChessPiece;
import exception.ResponseException;
import ui.ServerClient;
import websocket.NotificationHandler;

public class MenuMain{
        public static void main(String[] args) throws ResponseException {
            var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            System.out.println("♕ 240 Chess Client: " + piece);
            ServerClient serverClient = new ServerClient("http://localhost:8080");
            ConsolePreLogin.start(serverClient);
        }
}
