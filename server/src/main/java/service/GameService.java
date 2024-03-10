package service;

import Requests.CreateGameRequest;
import dataAccess.DataAccessAuth;
import dataAccess.DataAccessException;
import dataAccess.DataAccessGame;
import model.AuthToken;

import javax.xml.crypto.Data;

public class GameService {
    DataAccessGame dataAccess;
    DataAccessAuth dataAccessAuth;

    public GameService(DataAccessGame dataAccess, DataAccessAuth dataAccessAuth) {
        this.dataAccess = dataAccess;
        this.dataAccessAuth = dataAccessAuth;
    }

    public String createGame(CreateGameRequest request) throws DataAccessException {
        String gameName = request.getGameName();
        String authTokenString = request.getAuthToken();
        AuthToken authToken = dataAccessAuth.findAuthToken(authTokenString);
        if (authToken == null){
            throw new DataAccessException("Error: unauthorized");
        }
        if (gameName == null) {
            throw new DataAccessException("Error: bad request");
        }
        if (dataAccess.getGameName(gameName)){
            throw new DataAccessException("Error: bad request");
        }
        dataAccess.addGame(gameName);
        return dataAccess.getGameID(gameName);
    }
}
