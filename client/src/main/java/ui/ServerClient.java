package ui;

import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthToken;
import model.Game;
import model.User;
import server.ServerFacade;

import java.util.Arrays;

public class ServerClient {
    private final ServerFacade serverFacade;

    private final String serverUrl;



    public ServerClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

//    public String eval(String input) {
//        try {
//            var tokens = input.toLowerCase().split(" ");
//            var cmd = (tokens.length > 0) ? tokens[0] : "help";
//            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
//            return switch (cmd) {
//                case "login" -> login(params);
//                case "register"->register(params);
//                case "logout"->logout(params);
//                default -> help();
//            };
//        } catch (ResponseException ex) {
//            return ex.getMessage();
//        }
//    }

    public Boolean register(String... params) throws ResponseException {
        if (params.length >= 3) {
            try {
                User user = new User(params[1], params[2], params[3]);
                serverFacade.register(user);
                System.out.println("Register Successful");
                return true;
            }
            catch (ResponseException e){
                System.out.println("Already Registered");
                return false;
            }

        }
        else {
            System.out.println("register <username> <password> <email>");
            return false;
        }
    }

    public Boolean login(String... params) {
        if (params.length >= 2) {
            User user = new User(params[1], params[2], null);
            try {
                serverFacade.login(user);
                System.out.println("Login Successful");
                return true;
            }
            catch (ResponseException e){
                System.out.println("incorrect login");
                return false;
            }
        }
        else {
            System.out.println("login <username> <password>");
            return false;
        }
    }

    public void logout(String... params) throws ResponseException {
        if (params.length >= 1) {
            serverFacade.logout();
        }
    }

    public void clear() throws ResponseException {
            serverFacade.clear();
    }

    public void create(String... params) throws ResponseException {
        if (params.length >= 2) {
            try {
            Game game = new Game (params[1], null, null, null);
            serverFacade.create(game);
            System.out.println("Game Created");}
            catch (ResponseException e){
                System.out.println("Game already created");
            }
        }
        else {
            System.out.println("create <gamename>");
        }
    }

    public String list() throws ResponseException {
        var games = serverFacade.list();
        var result = new StringBuilder();
        var gson = new Gson();
        for (var game: games) {
            result.append(gson.toJson(game)).append('\n');
        }
        System.out.println("Game List:");
        return result.toString();
    }

    public Boolean join(String... params) {
        if (params.length < 2) {
            System.out.println("join <gameID> <white|black|empty>");
            return false;
        }
        Game game = new Game(null, params[1], null, null);
        try {
            serverFacade.join(game);
        } catch (ResponseException e) {
            System.out.println("invalid gameid");
            return false;
        }
        if (params.length == 2) {
            System.out.println("observing game");
            return true;
        } else {
            if (params[2].toUpperCase().equals("WHITE")) {
                game.setPlayerColor("WHITE");
                try {
                    serverFacade.join(game);
                    System.out.println("Joined Game");
                } catch (ResponseException e) {
                    System.out.println("white player already assigned");
                    return false;
                }
            } else {
                game.setPlayerColor("BLACK");
                try {
                    serverFacade.join(game);
                    System.out.println("Joined Game");
                } catch (ResponseException e) {
                    System.out.println("black player already assigned");
                    return false;
                }
            }
        }
        return true;
    }


    public String help(Boolean login) {
        if (login) {
            return """
                    - Register <username> <password> <email>
                    - login <username> <password>
                    - quit
                    """;
        }
        return """
                - create <gamename>
                - list
                - join <gameid> <white|black|empty>
                - observe <gameid>
                - quit
                - logout
                """;
    }
}
