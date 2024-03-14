package dataAccessTests;

import dataAccess.*;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import service.UserService;

public class AuthSQLTest {
    MySQLDataAccessAuth authAccess = new MySQLDataAccessAuth();


    User testUser = new User("Splash", "1234", "sparklytomato@gmail.com");

    public AuthSQLTest() throws DataAccessException {
    }

    @BeforeEach
    void start() {
//        Assertions.assertDoesNotThrow(userAccess::clearAllUsers);
        Assertions.assertDoesNotThrow(authAccess::clearAllAuth);
//        Assertions.assertDoesNotThrow(gameAccess::clearAllGames);
    }

    @Test
    @DisplayName("CreateAuthToken Success")
    void testCreateAuthTokenPass() {
        AuthToken authToken = Assertions.assertDoesNotThrow(() -> authAccess.createAuthToken(testUser.getName()));

//        Assertions.assertEquals(testUser.getName(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getName());
//        Assertions.assertEquals(testUser.getPassword(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getPassword());
//        Assertions.assertEquals(testUser.getEmail(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getEmail());
    }

    @Test
    @DisplayName("AddAuthToken Success")
    void testAddAuthTokenPass(){
        AuthToken authToken = Assertions.assertDoesNotThrow(()-> authAccess.createAuthToken(testUser.getName()));
        Assertions.assertDoesNotThrow(()->authAccess.addAuthToken(authToken));
    }

    @Test
    @DisplayName("FindAuthToken Success")
    void testFindAuthTokenPass(){
        AuthToken authToken = Assertions.assertDoesNotThrow(()-> authAccess.createAuthToken(testUser.getName()));
        Assertions.assertDoesNotThrow(()->authAccess.addAuthToken(authToken));
        Assertions.assertDoesNotThrow(()->authAccess.findAuthToken(authToken.getToken()));
    }

    @Test
    @DisplayName("RemoveAuthToken Success")
    void testRemoveAuthTokenPass(){
        AuthToken authToken = Assertions.assertDoesNotThrow(()-> authAccess.createAuthToken(testUser.getName()));
        Assertions.assertDoesNotThrow(()->authAccess.addAuthToken(authToken));
        Assertions.assertDoesNotThrow(()->authAccess.removeAuthToken(authToken));
    }
}
