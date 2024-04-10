package server;
import requests.*;
import dataAccess.*;
import model.AuthToken;
import model.Game;
import server.websocket.WebSocketHandler;
import spark.*;
import service.*;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


public class Server {

    MySQLDataAccessUser user = new MySQLDataAccessUser();
    MySQLDataAccessAuth auth = new MySQLDataAccessAuth();
    MySQLDataAccessGame game = new MySQLDataAccessGame();
    UserService userService = new UserService(user, auth);
    GameService gameService = new GameService(game, auth);
    ClearService clearService = new ClearService(user, auth, game);
    WebSocketHandler webSocketHandler = new WebSocketHandler();




    public int run(int desiredPort) {
        Spark.webSocket("/connect", webSocketHandler);
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");
        Spark.post("/user", this::RegisterHandler);
        Spark.post("/session", this::LoginHandler);
        Spark.post(("/game"), this::CreateGameHandler);
        Spark.put(("/game"), this::JoinGameHandler);
        Spark.get(("/game"), this::ListGamesHandler);
        Spark.delete("/db", this::ClearApplicationHandler);
        Spark.delete("/session", this::LogoutHandler);
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    // Registers the User
    public Object RegisterHandler(Request req, Response res) {
        try {
            RegisterRequest request = new Gson().fromJson(req.body(), RegisterRequest.class);
            AuthToken authToken = userService.createUser(request);
            res.status(200);
            return new Gson().toJson(authToken);
        }
        catch (DataAccessException e) {
            var message = e.getMessage();
            if (message.equals("Error: bad request")) {
                res.status(400);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            } else if (message.equals("Error: already taken")) {
                res.status(403);
                return new Gson().toJson(Map.of("message", e.getMessage()));

            } else {
                res.status(500);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Login the User
    public Object LoginHandler(Request req, Response res) {
        try {
            LoginRequest request = new Gson().fromJson(req.body(), LoginRequest.class);
            AuthToken authToken = userService.findLogin(request);
            res.status(200);
            return new Gson().toJson(authToken);
        }
        catch (DataAccessException e){
            var message = e.getMessage();
            if (message.equals("Error: unauthorized")){
                res.status(401);
                return new Gson().toJson(Map.of("message", e.getMessage()));

            }
            else {
                res.status(500);
                return new Gson().toJson(Map.of("message", e.getMessage()));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Logout the User
    public Object LogoutHandler(Request req, Response res) {
        try {
            String authToken = req.headers("Authorization");
            LogoutRequest request = new LogoutRequest(authToken);
            userService.removeLogout(request);
            res.status(200);
            return "{}";
        }
        catch (DataAccessException e){
            var message = e.getMessage();
            if (message.equals("Error: unauthorized")) {
                res.status(401);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
            else {
                res.status(500);
                return new Gson().toJson(Map.of("message", e.getMessage()));

            }
        }
    }

    // Creates the Game
    public Object CreateGameHandler(Request req, Response res) {
        try {
            String authToken = req.headers("Authorization");
            CreateGameRequest request = new Gson().fromJson(req.body(), CreateGameRequest.class);
            request.addAuthToken(authToken);
            Game game = gameService.createGame(request, authToken);
            res.status(200);
            return new Gson().toJson(game);
        }
        catch (DataAccessException e){
            var message = e.getMessage();
            if (message.equals("Error: unauthorized")) {
                res.status(401);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
            if (message.equals("Error: bad request")) {
                res.status(400);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
            else {
                res.status(500);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
        }
    }

    // Joins a Game
    public Object JoinGameHandler(Request req, Response res) {
        try {
            String authToken = req.headers("Authorization");
            JoinGameRequest request = new Gson().fromJson(req.body(), JoinGameRequest.class);
            request.addAuthToken(authToken);
            gameService.joinGame(request, authToken);
            res.status(200);
            return "{}";
        }
        catch (DataAccessException e) {
            var message = e.getMessage();
            if (message.equals("Error: unauthorized")) {
                res.status(401);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
            if (message.equals("Error: bad request")) {
                res.status(400);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
            if (message.equals("Error: already taken")) {
                res.status(403);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            } else {
                res.status(500);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
        }
    }

    // Lists the Game
    public Object ListGamesHandler(Request req, Response res) {
        try {
            String authToken = req.headers("Authorization");
            ListGamesRequest request = new ListGamesRequest(authToken);
            ArrayList<Game> games = gameService.listGames(request);
            res.status(200);
            System.out.println(new Gson().toJson(games));
            return new Gson().toJson(Map.of("games", games));
        }
        catch (DataAccessException e) {
            var message = e.getMessage();
            if (message.equals("Error: unauthorized")) {
                res.status(401);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
            else {
                res.status(500);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
        }
    }

    // Clears all the Games and Users
    public Object ClearApplicationHandler(Request req, Response res) {
        try {
            clearService.clearEverything();
            res.status(200);
            return "{}";
        }
        catch (DataAccessException e) {
                res.status(500);
                return new Gson().toJson(Map.of("message", e.getMessage()));
        }
    }
}
