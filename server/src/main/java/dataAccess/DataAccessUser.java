package dataAccess;

import model.*;

import javax.xml.crypto.Data;

public interface DataAccessUser {
    void addUser(String userName, String password, String email ) throws DataAccessException;

    User getUser(String userName) throws DataAccessException;

    AuthToken createAuthToken(String userName) throws DataAccessException;


    public void clearAllUsers() throws DataAccessException;

}

