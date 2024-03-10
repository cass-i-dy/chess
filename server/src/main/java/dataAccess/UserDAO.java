package dataAccess;
import model.AuthToken;
import model.User;
import java.util.ArrayList;

public class UserDAO implements DataAccessUser {

    ArrayList<User> userList = new ArrayList<>();

    public User getUser(String userName){
        for (User user:userList) {
            if (user.getName().equals(userName)){
                return user;
            }
        }
        return null;
    }

    public void addUser(String userName, String password, String email){
        User user = new User(userName, password, email);
        userList.add(user);
    }

    public AuthToken createAuthToken(String userName){
        return new AuthToken(userName);
    }

    public void clearAllUsers(){
        userList.clear();
    }

}
