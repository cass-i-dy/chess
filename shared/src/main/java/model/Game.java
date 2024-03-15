package model;

public class Game {
    String gameName;
    String gameID;
    String whiteUsername;
    String blackUsername;

    public Game(String gameName, String gameID){
        this.gameName = gameName;
        this.gameID = gameID;
        this.whiteUsername = "";
        this.blackUsername = "";

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
}
