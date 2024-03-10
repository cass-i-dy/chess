package dataAccess;
import model.Game;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public interface DataAccessGame {
    void addGame(String gameName) throws DataAccessException;
    String getGameID(String gameName) throws DataAccessException;
    Boolean getGameName(String gameName) throws DataAccessException;
    Game getGame(String gameID) throws DataAccessException;
    Boolean setGame(Game game, String playerColor, String userName) throws DataAccessException;
    ArrayList<Game> getList() throws DataAccessException;
    void clearAllGames() throws DataAccessException;

}
