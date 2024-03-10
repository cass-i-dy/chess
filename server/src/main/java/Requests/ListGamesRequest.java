package Requests;

public class ListGamesRequest {
    String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public ListGamesRequest(String authToken) {
        this.authToken = authToken;

    }
}
