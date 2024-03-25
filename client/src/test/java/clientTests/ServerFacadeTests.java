package clientTests;

import dataAccess.*;
import exception.ResponseException;
import model.AuthToken;
import model.Game;
import model.User;
import org.junit.jupiter.api.*;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.ListGamesRequest;
import requests.RegisterRequest;
import server.Server;
import server.ServerFacade;
import service.ClearService;
import ui.ServerClient;


public class ServerFacadeTests {

    private static ServerFacade serverFacade;

    private static Server server;

    static MySQLDataAccessUser userAccess = new MySQLDataAccessUser();
    static MySQLDataAccessAuth authAccess = new MySQLDataAccessAuth();
    static MySQLDataAccessGame gameAccess = new MySQLDataAccessGame();



    @BeforeAll
    public static void init() throws DataAccessException {
        server = new Server();
        var port = server.run(0);
        serverFacade = new ServerFacade("http://localhost:"+ port);
        System.out.println("Started test HTTP server on " + port);
        userAccess.clearAllUsers();
        authAccess.clearAllAuth();
        gameAccess.clearAllGames();
    }

    @BeforeEach
    public void start() throws DataAccessException {
        userAccess.clearAllUsers();
        authAccess.clearAllAuth();
        gameAccess.clearAllGames();
    }

    @AfterAll
    static void stopServer() throws DataAccessException {
        server.stop();
        userAccess.clearAllUsers();
        authAccess.clearAllAuth();
        gameAccess.clearAllGames();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    User testUser = new User("username", "password", "email");
    Game testGame = new Game("Spaced Out", null, null, null);


    @Test
    @DisplayName("Test Register")
    void testRegisterPass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
    }

    @Test
    @DisplayName("Test Login")
    void testLoginPass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.login(testUser));
    }

    @Test
    @DisplayName("Test Login")
    void testLoginFail() {
        Assertions.assertThrows(ResponseException.class,()-> serverFacade.login(testUser));
    }

    @Test
    @DisplayName("Test Logout")
    void testLogoutPass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
    }

    @Test
    @DisplayName("Test Create Game")
    void testCreatePass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
    }

    @Test
    @DisplayName("Test List Games")
    void testListGames() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        Assertions.assertDoesNotThrow(()->serverFacade.list());
    }


}
