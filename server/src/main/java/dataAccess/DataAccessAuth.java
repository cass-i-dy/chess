package dataAccess;
import model.AuthToken;
import org.junit.jupiter.api.function.Executable;

public interface DataAccessAuth {
    void addAuthToken(AuthToken authToken) throws DataAccessException;

    AuthToken findAuthToken(String authTokenString) throws DataAccessException;

    void removeAuthToken(AuthToken authToken) throws DataAccessException;

    AuthToken createAuthToken(String userName) throws DataAccessException;


    void clearAllAuth() throws DataAccessException;
}
