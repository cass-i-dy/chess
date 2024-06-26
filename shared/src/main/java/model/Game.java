package model;

import chess.ChessGame;

public class Game {
    String gameName;
    String gameID;
    String whiteUsername;
    String blackUsername;
    String playerColor;
    ChessGame chessGame;
    String victor;

    public Game(String gameName, String gameID, String whiteUsername, String blackUsername){
        this.gameName = gameName;
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.chessGame = new ChessGame();
    }

    public void setPlayerColor(String color) {
        playerColor = color;
    }

    public String getName(){
        return gameName;
    }

    public String getGameID(){
        return gameID;
    }

    public String getWhite(){
        return whiteUsername;
    }
    public String getBlack(){
        return blackUsername;
    }
    public void setWhite(String playerWhite){
        whiteUsername = playerWhite;
    }
    public void setBlack(String playerBlack){
        blackUsername = playerBlack;
    }
    public ChessGame getChessGame(){
        return chessGame;
    }
    public String getVictor(){
        return victor;
    }
    public void setVictor(String name) {
        this.victor = name;
    }
    public void updateChessGame(ChessGame chessGame){
        this.chessGame = chessGame;
    }
}
