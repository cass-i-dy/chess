package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;
import model.User;

public class MakeMove extends UserGameCommand {

    public String gameID;
    public ChessMove move;

    public MakeMove(String authToken) {
        super(authToken);
        this.commandType = CommandType.MAKE_MOVE;

    }

    public void setChessMove(ChessMove chessMove){
        this.move = chessMove;
    }
    public void setGameID(String id){
        this.gameID = id;
    }
}
