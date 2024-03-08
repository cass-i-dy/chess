package server;

import dataAccess.*;
import passoffTests.testClasses.TestModels;
import spark.*;

import service.UserService;

import com.google.gson.Gson;


public class Server {
    DataAccess user = new UserDAO();

    UserService userService = new UserService(user);

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::RegisterHandler);
        Spark.post("/session", this::LoginHandler);


//        Spark.delete("/db", this::ClearApplication);
//        Spark.delete("/session", this::ClearLogout);



        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }





    // Get new Login Info from client
    public Object RegisterHandler(Request req, Response res) throws DataAccessException {
            RegisterRequest request = new Gson().fromJson(req.body(), RegisterRequest.class );
            userService.createUser(request);
            return new Gson().toJson(res);

//        return null;
    }

    public Object LoginHandler(Request req, Response res) throws DataAccessException {
        LoginRequest request = new Gson().fromJson(req.body(), LoginRequest.class);
        userService.findLogin(request);
        return new Gson().toJson(res);
    }
    public void JoinGame(){

    }

    public Object ClearApplication(){
        return null;
    }

    public Object ClearLogout(){
        return null;
    }

    public Object ListGamesHandler(){
        return null;
    }
}
