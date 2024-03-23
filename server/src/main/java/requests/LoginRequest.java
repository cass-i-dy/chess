package requests;

public class LoginRequest {
    String userName;
    String password;

    public String getUsername(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public void setUserName(String UserName){
        this.userName = UserName;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
