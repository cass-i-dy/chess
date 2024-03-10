package service;
import dataAccess.*;

public class ClearService {

    DataAccessUser dataAccessUser;
    DataAccessAuth dataAccessAuth;
    DataAccessGame dataAccessGame;

    public ClearService(DataAccessUser dataAccessUser, DataAccessAuth dataAccessAuth, DataAccessGame dataAccessGame) {
        this.dataAccessUser = dataAccessUser;
        this.dataAccessAuth = dataAccessAuth;
        this.dataAccessGame = dataAccessGame;
    }

    public void clearEverything() throws DataAccessException{
        dataAccessUser.clearAllUsers();
        dataAccessAuth.clearAllAuth();
        dataAccessGame.clearAllGames();
    }
}
