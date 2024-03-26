package requests;

public class RegisterRequest {
    String username;
    String password;
    String email;

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setUserPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
