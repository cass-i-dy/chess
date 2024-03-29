package dataAccess;

import java.util.ArrayList;
import model.Game;

public class GameDAO implements DataAccessGame{
    ArrayList<Game> games =  new ArrayList<>();
    int countGameID = 1;

    public void addGame(String gameName) {
        String whiteUsername = null;
        String blackUsername = null;
        Game game = new Game(gameName, String.valueOf(countGameID),whiteUsername, blackUsername );
        countGameID += 1;
        games.add(game);

    }

    public String getGameID(String gameName) {
        for (Game game: games) {
            if (game.getName().equals(gameName)){
                return game.getGameID();
            }
        }
        return null;
    }

    public Boolean getGameName(String gameName){
        for (Game game:games) {
            if (game.getName().equals(gameName)){
                return true;
            }
        }
        return false;
    }

    public Game getGame(String gameID) {
        for (Game game:games){
            if (game.getGameID().equals(gameID)){
                return game;
            }
        }
        return null;
    }

    public Boolean setGame(Game game, String playerColor, String userName){
        if (playerColor == null) {
            return true;
        }
        if (playerColor.equals("WHITE") && (game.getWhite() == null)){
            game.setWhite(userName);
            return true;
        }
        else if (playerColor.equals("BLACK") && (game.getBlack() == null)){
            game.setBlack(userName);
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<Game> getList(){
        return games;
    }

    public void clearAllGames(){
        games.clear();
    }
}
