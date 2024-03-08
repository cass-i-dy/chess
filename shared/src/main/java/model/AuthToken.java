package model;

import java.util.UUID;

public class AuthToken {

    String token;
    String username;
    public AuthToken(String username){
        this.username = username;
        this.token = UUID.randomUUID().toString();
    }
}
