package ui;

import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthToken;
import model.Game;
import model.User;
import server.ServerFacade;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.Arrays;

public class ServerClient {
    private final ServerFacade serverFacade;

    private final String serverUrl;
    private final NotificationHandler notificationHandler;

    public AuthToken authToken;


    public ServerClient(String serverUrl, NotificationHandler notificationHandler) {
        serverFacade = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
    }


    public String register(String... params) {
        if (params.length >= 3) {
            try {
                User user = new User(params[1], params[2], params[3]);
                this.authToken = serverFacade.register(user);
                System.out.println("Register Successful");
                return String.format("You are registered as %s.", params[1]);
            }
            catch (ResponseException e){
                System.out.println("Already Registered");
                return null;
            }

        }
        else {
            System.out.println("register <username> <password> <email>");
            return null;
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

    public Boolean join(String... params) throws ResponseException {
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
            WebSocketFacade ws = new WebSocketFacade(serverUrl, notificationHandler);
            ws.joinObserve(params[1], "WHITE", authToken);
            return true;
        } else {
            if (params[2].toUpperCase().equals("WHITE")) {
                game.setPlayerColor("WHITE");
                try {
                    serverFacade.join(game);
                    WebSocketFacade ws = new WebSocketFacade(serverUrl, notificationHandler);
                    ws.joinUser(params[1], "WHITE", authToken);
                    System.out.println("Joined Game");
                } catch (ResponseException e) {
                    System.out.println(e.getMessage());
                    System.out.println("white player already assigned");
                    return false;
                }
            } else {
                game.setPlayerColor("BLACK");
                try {
                    serverFacade.join(game);
                    WebSocketFacade ws = new WebSocketFacade(serverUrl, notificationHandler);
                    ws.joinUser(params[1], "BLACK", authToken);
                    System.out.println("Joined Game");
                } catch (ResponseException e) {
                    System.out.println("black player already assigned");
                    return false;
                }
            }
        }
        return true;
    }

    public void makeMove(String... params){
        if (params.length < 4){
            System.out.println("too few arguments");}
        int row = Integer.parseInt(params[2]);
        int col = Integer.parseInt(params[3]);
//        serverFacade.update(row, col)
        try {
        WebSocketFacade ws = new WebSocketFacade(serverUrl, notificationHandler);
        ws.makeMove(row, col, authToken);}
        catch (Exception e) {
            System.out.println("invalid");
        }


    }


    public String help(String menu) {
        if (menu.equals("login")) {
            return """
                    - Register <username> <password> <email>
                    - login <username> <password>
                    - quit
                    """;
        }
        else if (menu.equals("menu")) {
            return """
                    - create <gamename>
                    - list
                    - join <gameid> <white|black|empty>
                    - observe <gameid>
                    - quit
                    - logout
                    """;
        }
        else {
            return """
                    - redraw chess board
                    - leave
                    - make move <number> <letter>
                    - resign
                    - highlight legal moves
                    """;
        }
    }

}
