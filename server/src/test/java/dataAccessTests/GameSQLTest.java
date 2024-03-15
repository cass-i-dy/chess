package dataAccessTests;

import dataAccess.*;
import model.Game;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import service.UserService;

public class GameSQLTest {
    MySQLDataAccessGame gameAccess = new MySQLDataAccessGame();
    ;


    Game testGame = new Game("Pokemon", "1", "", "");

    public GameSQLTest() throws DataAccessException {
    }

//    @BeforeEach
//    void start() {
//        Assertions.assertDoesNotThrow(userAccess::clearAllUsers);
//        Assertions.assertDoesNotThrow(authAccess::clearAllAuth);
//        Assertions.assertDoesNotThrow(gameAccess::clearAllGames);
//    }

    @Test
    @DisplayName("AddGame Success")
    void testAddGamePass() {
        Assertions.assertDoesNotThrow(() -> gameAccess.addGame(testGame.getName()));

//        Assertions.assertEquals(testUser.getName(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getName());
//        Assertions.assertEquals(testUser.getPassword(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getPassword());
//        Assertions.assertEquals(testUser.getEmail(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getEmail());
    }

    @Test
    @DisplayName("GetGameID Success")
    void testGetGameIDPass() {
        Assertions.assertDoesNotThrow(() -> gameAccess.getGameID(testGame.getName()));
    }

    @Test
    @DisplayName("GetGameName Success")
    void testGetGameNamePass() {
        Assertions.assertDoesNotThrow(() -> gameAccess.getGameName(testGame.getName()));
    }

    @Test
    @DisplayName("GetGame Success")
    void testGetGamePass() {
        Assertions.assertDoesNotThrow(() -> gameAccess.getGame(testGame.getGameID()));
    }

    @Test
    @DisplayName("ClearGames Success")
    void testClearGamesPass() {
        Assertions.assertDoesNotThrow(() -> gameAccess.clearAllGames());
    }


    @Test
    @DisplayName("JoinGame Success")
    void testJoinGameWhitePass() {
        Assertions.assertDoesNotThrow(()-> gameAccess.setGame(testGame, "WHITE", "Splash"));
    }


    @Test
    @DisplayName("JoinGame Success")
    void testJoinGameBlackPass() {
        Assertions.assertDoesNotThrow(()-> gameAccess.setGame(testGame, "BLACK", "Flame"));
    }

    @Test
    @DisplayName("ListGame Success")
    void testListGamePass() {
        Assertions.assertDoesNotThrow(()-> gameAccess.getList());
    }
}