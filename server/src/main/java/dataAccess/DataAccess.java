package dataAccess;

import dataAccess.DataAccessException;
import model.User;

public interface DataAccess {
    void addUser(String userName, String password, String email ) throws DataAccessException;

    User getUser(String userName) throws DataAccessException;
}
