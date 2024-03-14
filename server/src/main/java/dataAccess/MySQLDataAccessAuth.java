package dataAccess;

import model.AuthToken;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLDataAccessAuth extends MySQLDataAccess implements DataAccessAuth{

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authtokens (
              `username` varchar(255) NOT NULL,
              `authtoken` varchar(255) NOT NULL,
              PRIMARY KEY (`authtoken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public MySQLDataAccessAuth() throws DataAccessException {
        super();
        configureDatabase(createStatements);
    }

    @Override
    public void addAuthToken(AuthToken authToken) throws DataAccessException {
        String authString = authToken.getToken();
        String userName = authToken.getName();
        var statement = "INSERT INTO authtokens (username, authtoken) VALUES (?, ?)";
        executeUpdate(statement, userName, authString);

    }

    @Override
    public AuthToken findAuthToken(String authTokenString) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM authtokens WHERE authtoken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authTokenString);
                try(var rs = ps.executeQuery()) {
                    if (rs.next()){
                        return readAuth(rs);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private AuthToken readAuth(ResultSet rs) throws SQLException {
        String userName = rs.getString("username");
        String authTokenString = rs.getString("authtoken");
        if (userName == null || authTokenString == null){
            return null;
        }
        return new AuthToken(userName, authTokenString);
    }

    @Override
    public void removeAuthToken(AuthToken authToken) throws DataAccessException {

    }

    @Override
    public AuthToken createAuthToken(String userName) throws DataAccessException {
        return new AuthToken(userName, UUID.randomUUID().toString());
    }

    @Override
    public void clearAllAuth() throws DataAccessException {
        var statement = "TRUNCATE TABLE authtokens";
        executeUpdate(statement);

    }
}
