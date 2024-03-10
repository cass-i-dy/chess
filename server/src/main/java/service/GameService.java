package service;

import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.ListGamesRequest;
import dataAccess.DataAccessAuth;
import dataAccess.DataAccessException;
import dataAccess.DataAccessGame;
import model.AuthToken;
import model.Game;
import java.util.ArrayList;

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

    public void joinGame(JoinGameRequest request) throws DataAccessException {
        String gameID = request.getGameID();
        String playerColor = request.getPlayerColor();
        String authTokenString = request.getAuthToken();
        AuthToken authToken = dataAccessAuth.findAuthToken(authTokenString);
        if (authToken == null){
            throw new DataAccessException("Error: unauthorized");
        }
        if (gameID == null || playerColor == null){
            throw new DataAccessException("Error: bad request");
        }
        Game game = dataAccess.getGame(gameID);
        if (game == null){
            throw new DataAccessException("Error: bad request");
        }
        String userName = authToken.getName();
        if (!dataAccess.setGame(game, playerColor, userName)){
            throw new DataAccessException("Error: already taken");
        }
    }

    public ArrayList<Game> listGames(ListGamesRequest request) throws DataAccessException{
        String authTokenString = request.getAuthToken();
        AuthToken authToken = dataAccessAuth.findAuthToken(authTokenString);
        if (authToken == null){
            throw new DataAccessException("Error: unauthorized");
        }
        return dataAccess.getList();
    }
}
