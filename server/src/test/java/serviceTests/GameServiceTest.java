package serviceTests;
import Requests.*;
import org.junit.jupiter.api.*;
import service.*;
import dataAccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;


public class GameServiceTest {
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
    @DisplayName("CreateGame Success")
    void testCreateGamePass(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest));
    }

    @Test
    @DisplayName("CreateGame Fail")
    void testCreateGameFail(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest));
    }

    @Test
    @DisplayName("JoinGame Success")
    void testJoinGamePass(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        String gameID = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest));

        JoinGameRequest joinGameWhiteRequest = new JoinGameRequest("WHITE", gameID);
        JoinGameRequest joinGameBlackRequest = new JoinGameRequest("BLACK", gameID);
        joinGameBlackRequest.addAuthToken(authToken.getToken());
        joinGameWhiteRequest.addAuthToken(authToken.getToken());

        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameBlackRequest));
        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameWhiteRequest));

    }

    @Test
    @DisplayName("JoinGame Fail")
    void testJoinGameFail(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        String gameID = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest));

        JoinGameRequest joinGameBlackRequest = new JoinGameRequest("BLACK", gameID);
        joinGameBlackRequest.addAuthToken(authToken.getToken());

        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameBlackRequest));
        Assertions.assertThrows(DataAccessException.class, ()->gameService.joinGame(joinGameBlackRequest));

    }

    @Test
    @DisplayName("ListGame Success")
    void testListGamePass(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        String gameID = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest));

        JoinGameRequest joinGameWhiteRequest = new JoinGameRequest("WHITE", gameID);
        JoinGameRequest joinGameBlackRequest = new JoinGameRequest("BLACK", gameID);
        joinGameBlackRequest.addAuthToken(authToken.getToken());
        joinGameWhiteRequest.addAuthToken(authToken.getToken());

        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameBlackRequest));
        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameWhiteRequest));

        ListGamesRequest gamesRequest = new ListGamesRequest(authToken.getToken());

        Assertions.assertDoesNotThrow(()-> gameService.listGames(gamesRequest));

    }

    @Test
    @DisplayName("ListGame Fail")
    void testListGameFail(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        String gameID = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest));

        JoinGameRequest joinGameWhiteRequest = new JoinGameRequest("WHITE", gameID);
        JoinGameRequest joinGameBlackRequest = new JoinGameRequest("BLACK", gameID);
        joinGameBlackRequest.addAuthToken(authToken.getToken());
        joinGameWhiteRequest.addAuthToken(authToken.getToken());

        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameBlackRequest));
        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameWhiteRequest));

        ListGamesRequest gamesRequest = new ListGamesRequest("");

        Assertions.assertThrows(DataAccessException.class, ()-> gameService.listGames(gamesRequest));

    }


}
