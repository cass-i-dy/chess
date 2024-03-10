package requests;

public class JoinGameRequest {
    String playerColor;
    String gameID;
    String authToken;

    public JoinGameRequest(String playerColor, String gameID){
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public void addAuthToken(String auth){
        this.authToken = auth;
    }

    public String getPlayerColor(){
        return playerColor;
    }

    public String getGameID(){
        return gameID;
    }

    public String getAuthToken(){
        return authToken;
    }
}
