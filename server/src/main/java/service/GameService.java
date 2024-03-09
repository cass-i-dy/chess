package service;

import Requests.CreateGameRequest;
import dataAccess.DataAccessException;
import dataAccess.DataAccessGame;
import model.AuthToken;

public class GameService {
    DataAccessGame dataAccess;

    public GameService(DataAccessGame dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthToken createGame(CreateGameRequest request) throws DataAccessException {
        String gameName = request.getGameName();
        if (gameName == null) {
            throw new DataAccessException("Error: bad request");
        }
//        if (dataAccess.getAuthToken() = null){
//            throw new DataAccessException("Error: already taken");
//        }
//        dataAccess.addUser(username, password, email);
//        return dataAccess.createAuthToken(username);
        return null;
    }
}
