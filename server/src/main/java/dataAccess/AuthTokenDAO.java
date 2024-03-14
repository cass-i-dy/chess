package dataAccess;
import model.AuthToken;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.UUID;

public class AuthTokenDAO implements DataAccessAuth {

    ArrayList<AuthToken> authTokens = new ArrayList<>();

    public void addAuthToken(AuthToken authToken) throws DataAccessException {
        authTokens.add(authToken);
    }

    public AuthToken findAuthToken(String authTokenString) throws DataAccessException {
        for (AuthToken authToken:authTokens) {
            if (authToken.getToken().equals(authTokenString)){
                return authToken;
            }
        }
        return null;
    }

    public AuthToken createAuthToken(String userName)throws DataAccessException {
        return new AuthToken(userName, UUID.randomUUID().toString());
    }


    public void removeAuthToken(AuthToken authToken) throws DataAccessException{
        authTokens.remove(authToken);
    }

    public void clearAllAuth() throws DataAccessException {
        authTokens.clear();;
    }
}



