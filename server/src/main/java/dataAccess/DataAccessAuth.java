package dataAccess;

import model.AuthToken;

public interface DataAccessAuth {
    void addAuthToken(AuthToken authToken);

    AuthToken findAuthToken(String authTokenString);

    void removeAuthToken(AuthToken authToken);
}
