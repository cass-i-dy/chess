package dataAccessTests;

import dataAccess.*;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import service.UserService;

public class UserSQLTest {
    MySQLDataAccessUser userAccess = new MySQLDataAccessUser();
    MySQLDataAccessAuth authAccess = new MySQLDataAccessAuth();


    User testUser = new User("Splash", "1234", "sparklytomato@gmail.com");

    public UserSQLTest() throws DataAccessException {
    }

//    @BeforeEach
//    void start() {
//        Assertions.assertDoesNotThrow(userAccess::clearAllUsers);
//        Assertions.assertDoesNotThrow(authAccess::clearAllAuth);
//        Assertions.assertDoesNotThrow(gameAccess::clearAllGames);
//    }

    @Test
    @DisplayName("CreateUser Success")
    void testCreateUserPass(){
        Assertions.assertDoesNotThrow(()->userAccess.addUser(testUser.getName(), testUser.getPassword(), testUser.getEmail()));

//        Assertions.assertEquals(testUser.getName(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getName());
//        Assertions.assertEquals(testUser.getPassword(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getPassword());
//        Assertions.assertEquals(testUser.getEmail(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getEmail());
    }

    @Test
    @DisplayName("CreateUser Success")
    void testCreateAuthPass(){
        Assertions.assertDoesNotThrow(()->authAccess.createAuthToken(testUser.getName()));

//        Assertions.assertEquals(testUser.getName(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getName());
//        Assertions.assertEquals(testUser.getPassword(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getPassword());
//        Assertions.assertEquals(testUser.getEmail(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getEmail());
    }

    @Test
    @DisplayName("getUser Success")
    void testGetUserPass(){
        Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()));
    }

    @Test
    @DisplayName("clearUserTable Success")
    void testClearUserTablePass(){
        Assertions.assertDoesNotThrow(()->userAccess.clearAllUsers());
    }

}
