package server;

import Requests.*;
import dataAccess.*;
import model.AuthToken;
import spark.*;

import service.UserService;

import com.google.gson.Gson;

import java.util.Map;


public class Server {
    DataAccessUser user = new UserDAO();

    DataAccessAuth auth = new AuthTokenDAO();
    UserService userService = new UserService(user, auth);


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::RegisterHandler);
        Spark.post("/session", this::LoginHandler);


//        Spark.delete("/db", this::ClearApplication);
        Spark.delete("/session", this::LogoutHandler);



        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }





    // Get new Login Info from client
    public Object RegisterHandler(Request req, Response res) throws DataAccessException {
        try {
            RegisterRequest request = new Gson().fromJson(req.body(), RegisterRequest.class);
            AuthToken authToken = userService.createUser(request);
            res.status(200);
            return new Gson().toJson(authToken);
        }
        catch (DataAccessException e) {
            var message = e.getMessage();
            if (message == "Error: bad request") {
                res.status(400);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            } else if (message == "Error: already taken") {
                res.status(403);
                return new Gson().toJson(Map.of("message", e.getMessage()));

            } else {
                res.status(500);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
        }

    }

    public Object LoginHandler(Request req, Response res) throws DataAccessException {
        try {
            LoginRequest request = new Gson().fromJson(req.body(), LoginRequest.class);
            AuthToken authToken = userService.findLogin(request);
            res.status(200);
            return new Gson().toJson(authToken);
        }
        catch (DataAccessException e){
            var message = e.getMessage();
            if (message == "Error: unauthorized"){
                res.status(401);
                return new Gson().toJson(Map.of("message", e.getMessage()));

            }
            else {
                res.status(500);
                return new Gson().toJson(Map.of("message", e.getMessage()));

            }
        }
    }

    public Object LogoutHandler(Request req, Response res) throws DataAccessException {
        try {
            String authToken = req.headers("Authorization");
            LogoutRequest request = new LogoutRequest(authToken);
            userService.removeLogout(request);
            res.status(200);
            return "{}";
        }
        catch (DataAccessException e){
            var message = e.getMessage();
            if (message == "Error: unauthorized") {
                res.status(401);
                return new Gson().toJson(Map.of("message", e.getMessage()));
            }
            else {
                res.status(500);
                return new Gson().toJson(Map.of("message", e.getMessage()));

            }
        }
    }

    public void JoinGame(){

    }

    public Object ClearApplication(){
        return null;
    }


    public Object ListGamesHandler(){
        return null;
    }
}
