package dataAccessTests;

import dataAccess.*;
import model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
public class GameSQLTest {
    MySQLDataAccessGame gameAccess = new MySQLDataAccessGame();
    ;


    Game testGame = new Game("Pokemon", "1", null, null);


    @BeforeEach
    void start() {
        Assertions.assertDoesNotThrow(()->gameAccess.clearAllGames());
    }

    @Test
    @DisplayName("AddGame Success")
    void testAddGamePass() {
        Assertions.assertDoesNotThrow(() -> gameAccess.addGame(testGame.getName()));
    }

    @Test
    @DisplayName("GetGameID Success")
    void testGetGameIDPass() {
        Assertions.assertDoesNotThrow(() -> gameAccess.addGame(testGame.getName()));
        Assertions.assertDoesNotThrow(() -> gameAccess.getGameID(testGame.getName()));
    }

    @Test
    @DisplayName("GetGameID Fail")
    void testGetGameIDFail() {
        Assertions.assertNull(Assertions.assertDoesNotThrow(()-> gameAccess.getGameID(testGame.getName())));
    }

    @Test
    @DisplayName("GetGameName Success")
    void testGetGameNamePass() {
        Assertions.assertDoesNotThrow(() -> gameAccess.getGameName(testGame.getName()));
    }

    @Test
    @DisplayName("GetGameName Fail")
    void testGetGameNameFail() {
        Assertions.assertEquals(false, Assertions.assertDoesNotThrow(()-> gameAccess.getGameName(testGame.getName())));
    }

    @Test
    @DisplayName("GetGame Success")
    void testGetGamePass() {
        Assertions.assertDoesNotThrow(() -> gameAccess.getGame(testGame.getGameID()));
    }

    @Test
    @DisplayName("GetGame Fail")
    void testGetGameFail() {
        Assertions.assertNull( Assertions.assertDoesNotThrow(()-> gameAccess.getGame(testGame.getGameID())));
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
    @DisplayName("JoinGame Fail")
    void testJoinGameWhiteFail(){
        Assertions.assertDoesNotThrow(()-> gameAccess.setGame(testGame, "WHITE", "Splash"));
        Assertions.assertEquals(false, Assertions.assertDoesNotThrow(()->gameAccess.setGame(testGame, "WHITE", "Flame")));

    }


    @Test
    @DisplayName("JoinGame Success")
    void testJoinGameBlackPass() {
        Assertions.assertDoesNotThrow(()-> gameAccess.setGame(testGame, "BLACK", "Flame"));
    }

    @Test
    @DisplayName("JoinGame Fail")
    void testJoinGameBlackFail(){
        Assertions.assertDoesNotThrow(()-> gameAccess.setGame(testGame, "BLACK", "Splash"));
        Assertions.assertEquals(false, Assertions.assertDoesNotThrow(()->gameAccess.setGame(testGame, "BLACK", "Flame")));

    }


    @Test
    @DisplayName("ListGame Success")
    void testListGamePass() {
        Assertions.assertDoesNotThrow(()-> gameAccess.getList());
    }
}