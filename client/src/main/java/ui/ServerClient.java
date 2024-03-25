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

    public void register(String... params) throws ResponseException {
        if (params.length >= 3) {
            User user = new User(params[1], params[2], params[3]);
            serverFacade.register(user);
        }
        else {
            throw new ResponseException(400, "Expected: <yourname>");
        }
    }

    public void login(String... params) throws ResponseException {
        if (params.length >= 2) {
            User user = new User(params[1], params[2], null);
            serverFacade.login(user);
        }
        throw new ResponseException(400, "Expected: <yourname>");
    }

    public void logout(String... params) throws ResponseException {
        if (params.length >= 1) {
            serverFacade.logout();
        }
        throw new ResponseException(400, "Expected");
    }

    public void create(String... params) throws ResponseException {
        if (params.length >= 1) {
            Game game = new Game (params[1], null, null, null);
            serverFacade.create(game);
        }
    }

    public String list() throws ResponseException {
        var games = serverFacade.list();
        var result = new StringBuilder();
        var gson = new Gson();
        for (var game: games) {
            result.append(gson.toJson(game)).append('\n');
        }
        return result.toString();
    }

    public void join(String... params) throws ResponseException {
        if (params.length == 1) {
                Game game = new Game(null, params[1], null, null);
                serverFacade.join(game);
            }
        else {
            if (params[2].equals("WHITE")) {
                Game game = new Game(null, params[1], params[2], null );
                serverFacade.join(game);
            }
            if (params[2].equals("BLACK")) {
                Game game = new Game(null, params[1], null, params[2]);
                serverFacade.join(game);
            }
        }
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
                - help
                - logout
                """;
    }
}
