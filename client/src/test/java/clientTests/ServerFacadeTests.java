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



    @BeforeAll
    public static void init() throws DataAccessException {
        server = new Server();
        var port = server.run(0);
        serverFacade = new ServerFacade("http://localhost:"+ port);
        System.out.println("Started test HTTP server on " + port);
    }


    @AfterAll
    static void stopServer() throws DataAccessException {
        server.stop();
    }

    @BeforeEach
    void start() throws ResponseException{
        serverFacade.clear();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    User testUser = new User("username", "password", "email");

    User testUserLogin = new User("username", "password", null);
    Game testGame = new Game("Spaced Out", null, null, null);

    Game testCreatedGame = new Game("Spaced Out", "1", null, null);



    @Test
    @DisplayName("Test Register")
    void testRegisterPass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
    }

    @Test
    @DisplayName("Test Register")
    void testRegisterFail(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertThrows(ResponseException.class, ()->serverFacade.register(testUser));
    }

    @Test
    @DisplayName("Test Logout")
    void testLogoutPass1() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
    }

    @Test
    @DisplayName("Test Login")
    void testLoginPass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
        Assertions.assertDoesNotThrow(()->serverFacade.login(testUserLogin));
    }

    @Test
    @DisplayName("Test Login")
    void testLoginFail1() {

        Assertions.assertThrows(ResponseException.class,()->serverFacade.logout());


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
        Assertions.assertDoesNotThrow(()->serverFacade.login(testUserLogin));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
    }

    @Test
    @DisplayName("Test Logout")
    void testLogoutFail() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
        Assertions.assertDoesNotThrow(()->serverFacade.login(testUserLogin));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
        Assertions.assertThrows(ResponseException.class, ()->serverFacade.logout());


    }

    @Test
    @DisplayName("Test Create Game")
    void testCreatePass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
    }

    @Test
    @DisplayName("Test Create Game")
    void testCreateFail() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        Assertions.assertThrows(ResponseException.class, ()->serverFacade.create(testGame));
    }

    @Test
    @DisplayName("Test Create Game")
    void testCreateFail1() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Game game = new Game(null, null, null, null);
        Assertions.assertThrows(ResponseException.class, ()->serverFacade.create(game));
    }

    @Test
    @DisplayName("Test List Games")
    void testListGames() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        Assertions.assertDoesNotThrow(()->serverFacade.list());
    }

    @Test
    @DisplayName("Join Game")
        void testJoinWhiteGame(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setWhite("WHITE");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
    }

    @Test
    @DisplayName("Join Game")
    void testJoinBlackGame(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setBlack("BLACK");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
    }

    @Test
    @DisplayName("Join Game")
    void testJoinBothGame(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setBlack("BLACK");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
        testCreatedGame.setWhite("WHITE");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
    }

    @Test
    @DisplayName("Join Game")
    void testJoinAndObserveGameFail(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setBlack("BLACK");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
        Assertions.assertThrows(ResponseException.class,()->serverFacade.join(testGame));
    }

    @Test
    @DisplayName("Observe Game")
    void testJoinObserveEmptyGame(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
    }

    @Test
    @DisplayName("Fail Join Game")
    void failJoinWhiteGame() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setWhite("WHITE");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));

    }

    @Test
    @DisplayName("Fail Join Game")
    void failJoinBlackGame() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setWhite("Black");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));

    }




}
