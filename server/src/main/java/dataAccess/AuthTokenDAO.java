package dataAccess;
import model.AuthToken;
import model.User;

import java.util.ArrayList;

public class AuthTokenDAO implements DataAccessAuth {

    ArrayList<AuthToken> authTokens = new ArrayList<>();

    public void addAuthToken(AuthToken authToken){
        authTokens.add(authToken);
    }

    public AuthToken findAuthToken(String authTokenString){
        for (AuthToken authToken:authTokens) {
            if (authToken.getToken().equals(authTokenString)){
                return authToken;
            }
        }
        return null;
    }

    public void removeAuthToken(AuthToken authToken){
        authTokens.remove(authToken);
    }

    public void clearAllAuth() {
        authTokens.clear();;
    }
    }



