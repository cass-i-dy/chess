package model;

import java.util.UUID;

public class AuthToken {

    String authToken;
    String username;
    public AuthToken(String username){
        this.username = username;
        this.authToken = UUID.randomUUID().toString();
    }

    public String getName(){
        return username;
    }

    public String getToken(){
        return authToken;
    }

}
