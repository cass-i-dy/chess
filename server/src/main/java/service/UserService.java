package service;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import server.RegisterRequest;

public class UserService {
    DataAccess dataAccess;



    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void addUser(RegisterRequest request) throws DataAccessException {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        dataAccess.getUser(username);
        dataAccess.addUser(username, password, email);

    }
}
