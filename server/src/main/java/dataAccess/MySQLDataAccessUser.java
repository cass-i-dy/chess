package dataAccess;


import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDataAccessUser extends MySQLDataAccess implements DataAccessUser{

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  users (
              `username` varchar(255) NOT NULL,
              `password` varchar(255) NOT NULL,
              `email` varchar(255) NOT NULL ,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public MySQLDataAccessUser() {
    }
    @Override
    public void addUser(String userName, String password, String email) throws DataAccessException {
        configureDatabase(createStatements);
        var statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        executeUpdate(statement, userName, password, email);

    }

    @Override
    public User getUser(String userName) throws DataAccessException, SQLException {
        configureDatabase(createStatements);
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, userName);
                try(var rs = ps.executeQuery()) {
                    if (rs.next()){
                        return readUser(rs);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private User readUser(ResultSet rs) throws SQLException {
        String userName = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        if (userName == null || password == null || email == null){
            return null;
        }
        return new User(userName, password, email);
    }



    @Override
    public void clearAllUsers() throws DataAccessException {
        configureDatabase(createStatements);
        var statement = "TRUNCATE TABLE users";
        executeUpdate(statement);

    }


}
