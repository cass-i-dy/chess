package dataAccess;
import model.*;

public interface DataAccessUser {
    void addUser(String userName, String password, String email ) throws DataAccessException;

    User getUser(String userName) throws DataAccessException;

    AuthToken createAuthToken(String userName) throws DataAccessException;

    void clearAllUsers() throws DataAccessException;
}
