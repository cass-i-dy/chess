package dataAccess;

import model.AuthToken;
import model.User;

public class MySQLDataAccessUser extends MySQLDataAccess implements DataAccessUser{

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  users (
              `username` String NOT NULL,
              `password` String NOT NULL,
              `email` String NOT NULL ,
              PRIMARY KEY (`user`),
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public MySQLDataAccessUser() throws DataAccessException {
        super();
        configureDatabase(createStatements);
    }
    @Override
    public void addUser(String userName, String password, String email) throws DataAccessException {
        var statement = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";
        executeUpdate(statement, userName, password, email);

    }

//    @Override
//    public User getUser(String userName) throws DataAccessException {
//        try (var conn = DatabaseManager.getConnection()) {
//            var statement = "SELECT user FROM Users WHERE user=?";
//            try (var ps = conn.prepareStatement(statement)) {
//                ps.setInt(1, userName);
//                try(var rs = ps.executeQuery()) {
//                    if (rs.next()){
//                        return;
//                    }
//                }
//            }
//        }
//    }


    public User getUser(String userName) throws DataAccessException {
        return null;
    }


    @Override
    public void clearAllUsers() throws DataAccessException {

    }


}
