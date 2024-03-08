package service;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import server.RegisterRequest;
import model.*;
import server.LoginRequest;

public class UserService {
    DataAccess dataAccess;



    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthToken createUser(RegisterRequest request) throws DataAccessException {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        dataAccess.getUser(username);
        dataAccess.addUser(username, password, email);
        return dataAccess.createAuthToken(username);
    }

    public AuthToken findLogin(LoginRequest request) throws DataAccessException {
        String username = request.getUsername();
        String password = request.getPassword();
        dataAccess.getUser(username);
        return dataAccess.createAuthToken(username);

    }

}
