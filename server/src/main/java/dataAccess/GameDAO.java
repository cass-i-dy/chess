package dataAccess;

import java.util.ArrayList;

import model.Game;
import model.User;

public class GameDAO implements DataAccessGame{
    ArrayList<Game> games =  new ArrayList<>();
    int countGameID = 1;

    public void addGame(String gameName) {
        Game game = new Game(gameName, String.valueOf(countGameID));
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
}
