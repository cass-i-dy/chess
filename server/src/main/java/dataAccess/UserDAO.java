package dataAccess;
import model.User;

import java.util.Objects;
import java.util.ArrayList;

public class UserDAO implements DataAccess {

    ArrayList<User> userList = new ArrayList<>();


    public User getUser(String userName){
        for (User user:userList) {
            if (Objects.equals(user.getName(), userName)){
                return user;
            }
        }
        return null;
    }

    public void addUser(String userName, String password, String email){
        User user = new User(userName, password, email);
        userList.add(user);
    }
}
