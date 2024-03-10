package serviceTests;

import dataAccess.*;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.*;
import service.*;

public class ClearServiceTest {
    DataAccessUser userAccess = new UserDAO();
    DataAccessAuth authAccess = new AuthTokenDAO();
    DataAccessGame gameAccess = new GameDAO();
    UserService userService = new UserService(userAccess, authAccess);
    GameService gameService = new GameService(gameAccess, authAccess);

    ClearService clearService = new ClearService(userAccess, authAccess, gameAccess);

    User testUser = new User("Splash", "1234", "sparklytomato@gmail.com");

    @BeforeEach
    void start() {
        Assertions.assertDoesNotThrow(userAccess::clearAllUsers);
        Assertions.assertDoesNotThrow(authAccess::clearAllAuth);
        Assertions.assertDoesNotThrow(gameAccess::clearAllGames);
    }

    @Test
    @DisplayName("ClearAll Success")
    void testClearAllPass() {
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(() -> userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        String gameID = Assertions.assertDoesNotThrow(() -> gameService.createGame(gameRequest));

        JoinGameRequest joinGameWhiteRequest = new JoinGameRequest("WHITE", gameID);
        JoinGameRequest joinGameBlackRequest = new JoinGameRequest("BLACK", gameID);
        joinGameBlackRequest.addAuthToken(authToken.getToken());
        joinGameWhiteRequest.addAuthToken(authToken.getToken());

        Assertions.assertDoesNotThrow(() -> gameService.joinGame(joinGameBlackRequest));
        Assertions.assertDoesNotThrow(() -> gameService.joinGame(joinGameWhiteRequest));

        ListGamesRequest gamesRequest = new ListGamesRequest(authToken.getToken());

        Assertions.assertDoesNotThrow(() -> gameService.listGames(gamesRequest));

        Assertions.assertDoesNotThrow(()->clearService.clearEverything());
    }
}
