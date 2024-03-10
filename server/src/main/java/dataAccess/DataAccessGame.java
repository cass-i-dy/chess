package dataAccess;

public interface DataAccessGame {
    void addGame(String gameName);
    String getGameID(String gameName);

    Boolean getGameName(String gameName);
}
