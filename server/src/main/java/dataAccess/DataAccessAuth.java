package dataAccess;
import model.AuthToken;

public interface DataAccessAuth {
    void addAuthToken(AuthToken authToken) throws DataAccessException;

    AuthToken findAuthToken(String authTokenString) throws DataAccessException;

    void removeAuthToken(AuthToken authToken) throws DataAccessException;

    AuthToken createAuthToken(String userName);


    void clearAllAuth() throws DataAccessException;
}
