package dataAccess;
import model.*;

import java.sql.SQLException;

public interface DataAccessUser {
    void addUser(String userName, String password, String email ) throws DataAccessException;

    User getUser(String userName) throws DataAccessException, SQLException;


    void clearAllUsers() throws DataAccessException;
}

