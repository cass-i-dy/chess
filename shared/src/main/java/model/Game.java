package model;

public class Game {
    String gameName;
    String gameID;
    String whiteUserName = "";
    String blackUserName = "";

    public Game(String gameName, String gameID){
        this.gameName = gameName;
        this.gameID = gameID;
    }

    public String getName(){
        return gameName;
    }

    public String getGameID(){
        return gameID;
    }

    public String getWhite(){
        return whiteUserName;
    }
    public String getBlack(){
        return blackUserName;
    }
    public void setWhite(String playerWhite){
        whiteUserName = playerWhite;
    }
    public void setBlack(String playerBlack){
        blackUserName = playerBlack;
    }
}
