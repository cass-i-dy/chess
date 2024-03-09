package dataAccess;

import model.AuthToken;

public interface DataAccessAuth {
    public void addAuthToken(AuthToken authToken);

    AuthToken findAuthToken(String authTokenString);

    public void removeAuthToken(AuthToken authToken);
}
