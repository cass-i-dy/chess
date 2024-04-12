package dataAccess;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import model.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLDataAccessGame extends MySQLDataAccess implements DataAccessGame{

    int countGameID = 1;

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `gamename` varchar(255) NOT NULL,
              `gameid` varchar(255) NOT NULL,
              `whiteusername` varchar(255) ,
              `blackusername` varchar(255) ,
              `victor` varchar(255) ,
              `gameobject` TEXT ,

              PRIMARY KEY (`gameid`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    @Override
    public void addGame(String gameName) throws DataAccessException {
        configureDatabase(createStatements);
        ChessGame chessGame = new ChessGame();
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();
        chessGame.setBoard(chessBoard);
        chessGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        String gameObjectJson = new Gson().toJson(chessGame);
        var statement = "INSERT INTO games (gamename, gameid, whiteusername, blackusername, victor, gameobject) VALUES (?,?,?,?,?,?)";
        executeUpdate(statement, gameName, countGameID, null, null, null, gameObjectJson);
        countGameID++;
    }

    @Override
    public String getGameID(String gameName) throws DataAccessException {
        configureDatabase(createStatements);
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games WHERE gamename=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameName);
                try(var rs = ps.executeQuery()) {
                    if (rs.next()){
                        Game game = readGame(rs);
                        assert game != null;
                        return game.getGameID();
                    }
                }
            }
        }
        catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private Game readGame(ResultSet rs) throws SQLException {
        String gameName = rs.getString("gamename");
        String gameID = rs.getString("gameID");
        String whiteUserName = rs.getString("whiteusername");
        String blackUserName = rs.getString("blackusername");
        String victor = rs.getString("victor");
        String chessGame = rs.getString("gameobject");
        if (gameName == null || gameID == null){
            return null;
        }
        Game game = new Game(gameName, gameID, whiteUserName, blackUserName);
        game.setVictor(victor);
        game.updateChessGame(new Gson().fromJson(chessGame, ChessGame.class));
        return game;
    }

    @Override
    public Boolean getGameName(String gameName) throws DataAccessException {
        configureDatabase(createStatements);
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games WHERE gamename=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameName);
                try(var rs = ps.executeQuery()) {
                    if (rs.next()){
                        Game game = readGame(rs);
                        return game != null;
                    }
                }
            }
        }
        catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return false;
    }

    @Override
    public Game getGame(String gameID) throws DataAccessException {
        configureDatabase(createStatements);
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games WHERE gameid=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameID);
                try(var rs = ps.executeQuery()) {
                    if (rs.next()){
                        return readGame(rs);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public Boolean setGame(Game game, String playerColor, String userName) throws DataAccessException {
        configureDatabase(createStatements);
        if (playerColor == null) {
            return true;
        }
        if (playerColor.equals("WHITE") && (game.getWhite() == null)){
            game.setWhite(userName);
            var statementDelete = "DELETE FROM games WHERE gameid = ?";
            executeUpdate(statementDelete, game.getGameID());
            var statementInsert = "INSERT INTO games (gamename, gameid, whiteusername, blackusername, victor, gameobject) Values(?,?,?,?,?,?) ";

            executeUpdate(statementInsert, game.getName(), game.getGameID(), game.getWhite(), game.getBlack(), game.getVictor(), new Gson().toJson(game.getChessGame()));
            return true;
        }
        else if (playerColor.equals("BLACK") && (game.getBlack() == null)){
            game.setBlack(userName);
            var statementDelete = "DELETE FROM games WHERE gameid = ?";
            executeUpdate(statementDelete, game.getGameID());
            var statementInsert = "INSERT INTO games (gamename, gameid, whiteusername, blackusername, victor, gameobject) Values(?,?,?,?,?,?) ";
            executeUpdate(statementInsert, game.getName(), game.getGameID(), game.getWhite(), game.getBlack(), game.getVictor(), new Gson().toJson(game.getChessGame()));
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public ArrayList<Game> getList() throws DataAccessException {
        configureDatabase(createStatements);
        var result = new ArrayList<Game>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gamename, gameid, whiteusername, blackusername FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;
    }

    @Override
    public void clearAllGames() throws DataAccessException {
        configureDatabase(createStatements);
        countGameID = 1;
        var statement = "TRUNCATE TABLE games";
        executeUpdate(statement);
    }

    public void updateGame(Game game) throws DataAccessException {
        configureDatabase(createStatements);
        var statementDelete = "DELETE FROM games WHERE gameid = ?";
        executeUpdate(statementDelete, game.getGameID());
        var statementInsert = "INSERT INTO games (gamename, gameid, whiteusername, blackusername, victor, gameobject) Values(?,?,?,?,?,?) ";
        executeUpdate(statementInsert, game.getName(), game.getGameID(), game.getWhite(), game.getBlack(), game.getVictor(), new Gson().toJson(game.getChessGame()));
    }
}
