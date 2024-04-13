package requests;

public class LoginRequest {
    String username;
    String password;

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public void setUsername(String Username){
        this.username = Username;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
