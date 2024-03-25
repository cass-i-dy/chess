package requests;

public class CreateGameRequest {
    String authToken;
    String gameName;

    String whiteUsername;

    String blackUsername;

    public String getGameName(){
        return gameName;
    }
    public String getAuthToken(){
        return authToken;
    }

    public CreateGameRequest(String authToken, String gameName){
        this.authToken = authToken;
        this.gameName = gameName;
    }

    public void addAuthToken(String auth) {
        authToken = auth;
    }
}
