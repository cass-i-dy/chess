package dataAccess;

import model.Game;

public interface DataAccessGame {
    void addGame(String gameName);
    String getGameID(String gameName);

    Boolean getGameName(String gameName);

    Game getGame(String gameID);
    Boolean setGame(Game game, String playerColor, String userName);

}
