package Requests;

import model.AuthToken;

public class LogoutRequest {
    String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public LogoutRequest(String authToken) {
        this.authToken = authToken;

    }
}
