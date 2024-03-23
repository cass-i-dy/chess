package requests;

public class RegisterRequest {
    String userName;
    String password;
    String email;

    public String getUsername(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public void setUserName(String username){
        this.userName = username;
    }
    public void setUserPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
