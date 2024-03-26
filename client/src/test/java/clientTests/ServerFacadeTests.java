package clientTests;


import exception.ResponseException;
import model.Game;
import model.User;
import org.junit.jupiter.api.*;

import server.Server;
import server.ServerFacade;



public class ServerFacadeTests {

    private static ServerFacade serverFacade;

    private static Server server;



    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        serverFacade = new ServerFacade("http://localhost:"+ port);
        System.out.println("Started test HTTP server on " + port);
    }


    @AfterAll
    static void stopServer() {
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
    @DisplayName("Test Register Pass")
    void testRegisterPass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
    }

    @Test
    @DisplayName("Test Register Fail")
    void testRegisterFail(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertThrows(ResponseException.class, ()->serverFacade.register(testUser));
    }

    @Test
    @DisplayName("Test Logout1 Pass")
    void testLogoutPass1() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
    }

    @Test
    @DisplayName("Test Login Pass")
    void testLoginPass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
        Assertions.assertDoesNotThrow(()->serverFacade.login(testUserLogin));
    }

    @Test
    @DisplayName("Test Login Fail1")
    void testLoginFail1() {

        Assertions.assertThrows(ResponseException.class,()->serverFacade.logout());


    }

    @Test
    @DisplayName("Test Login Fail")
    void testLoginFail() {
        Assertions.assertThrows(ResponseException.class,()-> serverFacade.login(testUser));
    }

    @Test
    @DisplayName("Test Logout Pass")
    void testLogoutPass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
        Assertions.assertDoesNotThrow(()->serverFacade.login(testUserLogin));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
    }

    @Test
    @DisplayName("Test Logout Fail")
    void testLogoutFail() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
        Assertions.assertDoesNotThrow(()->serverFacade.login(testUserLogin));
        Assertions.assertDoesNotThrow(()->serverFacade.logout());
        Assertions.assertThrows(ResponseException.class, ()->serverFacade.logout());


    }

    @Test
    @DisplayName("Test Create Game Pass")
    void testCreatePass() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
    }

    @Test
    @DisplayName("Test Create Game Fail")
    void testCreateFail() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        Assertions.assertThrows(ResponseException.class, ()->serverFacade.create(testGame));
    }

    @Test
    @DisplayName("Test Create Game Fail1")
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
    @DisplayName("Join Game White")
        void testJoinWhiteGame(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setWhite("WHITE");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
    }

    @Test
    @DisplayName("Join Game Black")
    void testJoinBlackGame(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setBlack("BLACK");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
    }

    @Test
    @DisplayName("Join Game Both")
    void testJoinBothGame(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setBlack("BLACK");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
        testCreatedGame.setWhite("WHITE");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
    }

    @Test
    @DisplayName("Join and Observe Game Fail")
    void testJoinAndObserveGameFail(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setBlack("BLACK");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
        Assertions.assertThrows(ResponseException.class,()->serverFacade.join(testGame));
    }

    @Test
    @DisplayName("Observe Empty Game ")
    void testJoinObserveEmptyGame(){
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
    }

    @Test
    @DisplayName("Fail Join White Game")
    void failJoinWhiteGame() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setWhite("WHITE");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));

    }

    @Test
    @DisplayName("Fail Join Black Game")
    void failJoinBlackGame() {
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
        Assertions.assertDoesNotThrow(()->serverFacade.create(testGame));
        testCreatedGame.setWhite("Black");
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));
        Assertions.assertDoesNotThrow(()->serverFacade.join(testCreatedGame));

    }




}
