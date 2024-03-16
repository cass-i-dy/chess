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

import java.sql.SQLException;

public class AuthSQLTest {
    MySQLDataAccessAuth authAccess = new MySQLDataAccessAuth();


    User testUser = new User("Splash", "1234", "sparklytomato@gmail.com");


    @BeforeEach
    void start() {
        Assertions.assertDoesNotThrow(authAccess::clearAllAuth);
    }

    @Test
    @DisplayName("CreateAuthToken Success")
    void testCreateAuthTokenPass() {
        AuthToken authToken = Assertions.assertDoesNotThrow(() -> authAccess.createAuthToken(testUser.getName()));
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
    @DisplayName("FindAuthToken Fail")
    void testFindAuthTokenFail(){
        AuthToken authToken = Assertions.assertDoesNotThrow(()-> authAccess.createAuthToken(testUser.getName()));
        Assertions.assertNull(Assertions.assertDoesNotThrow(()->authAccess.findAuthToken(authToken.getToken())));
    }

    @Test
    @DisplayName("RemoveAuthToken Success")
    void testRemoveAuthTokenPass(){
        AuthToken authToken = Assertions.assertDoesNotThrow(()-> authAccess.createAuthToken(testUser.getName()));
        Assertions.assertDoesNotThrow(()->authAccess.addAuthToken(authToken));
        Assertions.assertDoesNotThrow(()->authAccess.removeAuthToken(authToken));
    }

    @Test
    @DisplayName("RemoveAuthToken Fail")
    void testRemoveAuthTokenFail(){
        AuthToken authToken = Assertions.assertDoesNotThrow(()-> authAccess.createAuthToken(testUser.getName()));
        Assertions.assertDoesNotThrow(()->authAccess.removeAuthToken(authToken));
        Assertions.assertNull(Assertions.assertDoesNotThrow(()->authAccess.findAuthToken(authToken.getToken())));
    }

    @Test
    @DisplayName("ClearAuthToken Success")
    void testClearAuthTokenSuccess(){
        AuthToken authToken = Assertions.assertDoesNotThrow(()-> authAccess.createAuthToken(testUser.getName()));
        Assertions.assertDoesNotThrow(()->authAccess.addAuthToken(authToken));
        Assertions.assertDoesNotThrow(()->authAccess.clearAllAuth());
        Assertions.assertNull(Assertions.assertDoesNotThrow(()->authAccess.findAuthToken(authToken.getToken())));
    }
}
