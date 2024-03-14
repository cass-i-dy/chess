package service;
import dataAccess.DataAccessAuth;
import dataAccess.DataAccessUser;
import dataAccess.DataAccessException;
import model.*;
import requests.*;

import java.sql.SQLException;
import java.util.UUID;

public class UserService {
    DataAccessUser dataAccess;
    DataAccessAuth authAccess;


    public UserService(DataAccessUser dataAccess, DataAccessAuth authAccess) {
        this.dataAccess = dataAccess;
        this.authAccess = authAccess;
    }

    public AuthToken createUser(RegisterRequest request) throws DataAccessException, SQLException {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        if (username == null || password == null || email == null){
            throw new DataAccessException("Error: bad request");
        }
        if (dataAccess.getUser(username) != null){
            throw new DataAccessException("Error: already taken");
        }
        dataAccess.addUser(username, password, email);
        // make create auth tken here insead of userdoa
        AuthToken authToken = authAccess.createAuthToken(username);
        authAccess.addAuthToken(authToken);
        return authToken;
    }

    public AuthToken findLogin(LoginRequest request) throws DataAccessException, SQLException {
        String username = request.getUsername();
        String password = request.getPassword();
        User user = dataAccess.getUser(username);
        if (user == null){
            throw new DataAccessException("Error: unauthorized");
        }
        if(!user.getPassword().equals(password)) {
            throw new DataAccessException("Error: unauthorized");
        }
        AuthToken authToken =  authAccess.createAuthToken(username);
        authAccess.addAuthToken(authToken);
        return authToken;

    }

    public void removeLogout(LogoutRequest request) throws DataAccessException {
        if (request == null){
            throw new DataAccessException("Error: unauthorized");
        }
        String authTokenString = request.getAuthToken();
        AuthToken authToken = authAccess.findAuthToken(authTokenString);
        if (authToken == null){
            throw new DataAccessException("Error: unauthorized");
        }
        authAccess.removeAuthToken(authToken);
    }
}
