package serviceTests;
import Requests.*;
import org.junit.jupiter.api.*;
import service.*;
import dataAccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;


public class UserServiceTest {
    DataAccessUser userAccess = new UserDAO();
    DataAccessAuth authAccess = new AuthTokenDAO();
    DataAccessGame gameAccess = new GameDAO();
    UserService userService = new UserService(userAccess, authAccess);
    GameService gameService = new GameService(gameAccess, authAccess);

    ClearService clearService = new ClearService(userAccess, authAccess, gameAccess);

    User testUser = new User("Splash", "1234", "sparklytomato@gmail.com");

    @BeforeEach
    void start() {
        Assertions.assertDoesNotThrow(userAccess::clearAllUsers);
        Assertions.assertDoesNotThrow(authAccess::clearAllAuth);
        Assertions.assertDoesNotThrow(gameAccess::clearAllGames);
    }

    @Test
    @DisplayName("Register Success")
    void testRegisterPass(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        Assertions.assertEquals(testUser.getName(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getName());
        Assertions.assertEquals(testUser.getPassword(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getPassword());
        Assertions.assertEquals(testUser.getEmail(), (Assertions.assertDoesNotThrow(()->userAccess.getUser(testUser.getName()))).getEmail());
    }

    @Test
    @DisplayName("Register Failed")
    void testRegisterUserNameFail(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(()->userService.createUser(request));

        Assertions.assertThrows(DataAccessException.class, ()->userService.createUser(request));

    }

    @Test
    @DisplayName("Register Failed")
    void testRegisterEmptyFail(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setEmail(testUser.getEmail());

        Assertions.assertThrows(DataAccessException.class, ()->userService.createUser(request));

    }

    @Test
    @DisplayName("Login Success")
    void testLoginPass() {
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(testUser.getName());
        loginRequest.setPassword(testUser.getPassword());

        AuthToken authToken = Assertions.assertDoesNotThrow(() -> userService.createUser(request));

        Assertions.assertDoesNotThrow(() -> userService.findLogin(loginRequest));
    }

    @Test
    @DisplayName("Login Success")
    void testLoginFail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(testUser.getName());
        loginRequest.setPassword(testUser.getPassword());

        Assertions.assertThrows(DataAccessException.class, ()->userService.findLogin(loginRequest));
    }

    @Test
    @DisplayName("Login Success")
    void testLoginEmptyFail() {
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(testUser.getName());
        loginRequest.setPassword("ooooo");

        AuthToken authToken = Assertions.assertDoesNotThrow(() -> userService.createUser(request));

        Assertions.assertThrows(DataAccessException.class, () -> userService.findLogin(loginRequest));
    }

    @Test
    @DisplayName("Logout Success")
    void testLogoutPass() {
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        AuthToken authToken = Assertions.assertDoesNotThrow(() -> userService.createUser(request));

        LogoutRequest logoutRequest = new LogoutRequest(authToken.getToken());

        Assertions.assertDoesNotThrow(() -> userService.removeLogout(logoutRequest));

    }

    @Test
    @DisplayName("Logout Success")
    void testLogoutFail() {
        RegisterRequest request = new RegisterRequest();
        request.setUserName(testUser.getName());
        request.setUserPassword(testUser.getPassword());
        request.setEmail(testUser.getEmail());

        LogoutRequest logoutRequest = new LogoutRequest("");

        Assertions.assertThrows(DataAccessException.class, () -> userService.removeLogout(logoutRequest));


    }



}
