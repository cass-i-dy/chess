package clientTests;

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
import ui.ServerClient;


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


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }


    @Test
    @DisplayName("Test Register")
    void testRegister() {
        User testUser = new User("username", "password", "email");
        Assertions.assertDoesNotThrow(()->serverFacade.register(testUser));
    }
}
