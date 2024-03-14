package model;

import java.util.UUID;

public class AuthToken {

    String authToken;
    String username;
    public AuthToken(String username, String authToken){
        this.username = username;
        this.authToken = authToken;
    }

    public String getName(){
        return username;
    }

    public String getToken(){
        return authToken;
    }

}
