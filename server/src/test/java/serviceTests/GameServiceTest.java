package serviceTests;
import requests.*;
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

        Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest, authToken.getToken()));
    }

    @Test
    @DisplayName("CreateGame Fail")
    void testCreateGameFail(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), null);

        Assertions.assertThrows(DataAccessException.class, ()->gameService.createGame(gameRequest, authToken.getToken()));
    }

    @Test
    @DisplayName("CreateGame Fail")
    void testCreateGameAuthFail(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest("", "Exploding Chess");

        Assertions.assertThrows(DataAccessException.class, ()->gameService.createGame(gameRequest, ""));
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

        Game game = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest, authToken.getToken()));

        JoinGameRequest joinGameWhiteRequest = new JoinGameRequest("WHITE", game.getGameID());
        JoinGameRequest joinGameBlackRequest = new JoinGameRequest("BLACK", game.getGameID());
        joinGameBlackRequest.addAuthToken(authToken.getToken());
        joinGameWhiteRequest.addAuthToken(authToken.getToken());

        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameBlackRequest, authToken.getToken()));
        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameWhiteRequest, authToken.getToken()));
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

        Game game = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest, authToken.getToken()));

        JoinGameRequest joinGameBlackRequest = new JoinGameRequest("BLACK", game.getGameID());
        joinGameBlackRequest.addAuthToken(authToken.getToken());

        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameBlackRequest, authToken.getToken()));
        Assertions.assertThrows(DataAccessException.class, ()->gameService.joinGame(joinGameBlackRequest, authToken.getToken()));
    }

    @Test
    @DisplayName("JoinGame Fail")
    void testJoinGameWatch(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        Game game = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest, authToken.getToken()));

        JoinGameRequest joinGameBlackRequest = new JoinGameRequest(null, game.getGameID());

        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameBlackRequest, authToken.getToken()));
    }

    @Test
    @DisplayName("JoinGame Fail")
    void testJoinAuthFail(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        Game game = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest, authToken.getToken()));

        JoinGameRequest joinGameBlackRequest = new JoinGameRequest("BLACK", game.getGameID());

        Assertions.assertThrows(DataAccessException.class, ()->gameService.joinGame(joinGameBlackRequest, ""));

    }

    @Test
    @DisplayName("JoinGame Fail")
    void testJoinGameWhileFail(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        Game game = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest, authToken.getToken()));

        JoinGameRequest joinGameWhiteRequest = new JoinGameRequest("WHITE", game.getGameID());
        joinGameWhiteRequest.addAuthToken(authToken.getToken());

        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameWhiteRequest, authToken.getToken()));
        Assertions.assertThrows(DataAccessException.class, ()->gameService.joinGame(joinGameWhiteRequest, authToken.getToken()));
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

        Game game = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest, authToken.getToken()));

        JoinGameRequest joinGameWhiteRequest = new JoinGameRequest("WHITE", game.getGameID());
        JoinGameRequest joinGameBlackRequest = new JoinGameRequest("BLACK", game.getGameID());
        joinGameBlackRequest.addAuthToken(authToken.getToken());
        joinGameWhiteRequest.addAuthToken(authToken.getToken());

        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameBlackRequest, authToken.getToken()));
        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameWhiteRequest, authToken.getToken()));

        ListGamesRequest gamesRequest = new ListGamesRequest(authToken.getToken());

        Assertions.assertDoesNotThrow(()-> gameService.listGames(gamesRequest));
    }
    @Test
    @DisplayName("ListGame Fail")
    void testListGameAuthFail(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        CreateGameRequest gameRequest = new CreateGameRequest(authToken.getToken(), "Exploding Chess");

        Game game = Assertions.assertDoesNotThrow(()->gameService.createGame(gameRequest, authToken.getToken()));

        JoinGameRequest joinGameWhiteRequest = new JoinGameRequest("WHITE", game.getGameID());
        JoinGameRequest joinGameBlackRequest = new JoinGameRequest("BLACK", game.getGameID());
        joinGameBlackRequest.addAuthToken(authToken.getToken());
        joinGameWhiteRequest.addAuthToken(authToken.getToken());

        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameBlackRequest, authToken.getToken()));
        Assertions.assertDoesNotThrow(()->gameService.joinGame(joinGameWhiteRequest, authToken.getToken()));

        ListGamesRequest gamesRequest = new ListGamesRequest("");

        Assertions.assertThrows(DataAccessException.class, ()-> gameService.listGames(gamesRequest));
    }


}
