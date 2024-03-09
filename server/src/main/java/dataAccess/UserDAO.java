package dataAccess;
import model.AuthToken;
import model.User;

import java.util.Objects;
import java.util.ArrayList;

public class UserDAO implements DataAccessUser {

    ArrayList<User> userList = new ArrayList<>();

@Override
    public User getUser(String userName){
        for (User user:userList) {
            if (user.getName().equals(userName)){
                return user;
            }
        }
        return null;
    }
@Override
    public void addUser(String userName, String password, String email){
        User user = new User(userName, password, email);
        userList.add(user);
    }
@Override
    public AuthToken createAuthToken(String userName){
        return new AuthToken(userName);
    }
@Override
    public void removeUser(User user){
        userList.remove(user);
    }

}
