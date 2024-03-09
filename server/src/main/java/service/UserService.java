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
        if (username == null || password == null || email == null){
            throw new DataAccessException("Error: bad request");
        }
        if (dataAccess.getUser(username) != null){
            throw new DataAccessException("Error: already taken");
        }
        dataAccess.addUser(username, password, email);
        return dataAccess.createAuthToken(username);
    }

    public AuthToken findLogin(LoginRequest request) throws DataAccessException {
        String username = request.getUsername();
        String password = request.getPassword();
        User user = dataAccess.getUser(username);
        if (user.getName() == null){
            throw new DataAccessException("Error: unauthorized");
        }
        if(user.getPassword() != password) {
            throw new DataAccessException("Error: unauthorized");
        }
        return dataAccess.createAuthToken(username);

    }

}
