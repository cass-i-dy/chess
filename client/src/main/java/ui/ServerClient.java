package ui;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthToken;
import model.Game;
import model.User;
import server.ServerFacade;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerClient extends NotificationHandler {
    private final ServerFacade serverFacade;

    private final String serverUrl;
    public AuthToken authToken;

    public ChessGame chessGame;

    public WebSocketFacade ws;




    public ServerClient(String serverUrl) throws ResponseException {
        serverFacade = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.ws = new WebSocketFacade(serverUrl, this);

    }

    public void notify(Notification notification) {
        this.notification = notification;

        if (notification.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)){
            LoadGame loadGame = new Gson().fromJson(notification.getMessage(), LoadGame.class);
            Game game = new Gson().fromJson(loadGame.getGame(), Game.class);
            chessGame = game.getChessGame();
        }
        if (notification.getServerMessageType().equals(ServerMessage.ServerMessageType.ERROR)){
            ErrorMessage errorMessage  = new Gson().fromJson(notification.getMessage(), ErrorMessage.class);
            System.out.println(errorMessage.getMessage());
        }
        if (notification.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)){
            System.out.println(notification.getMessage());
        }

    }

    public void loadGameNotify(LoadGame loadGame){
        Game game = new Gson().fromJson(loadGame.getGame(), Game.class);
        chessGame = game.getChessGame();
        ConsoleGame.printGame(chessGame.getBoard());
    }

    public void errorNotify(ErrorMessage errorMessage){
        System.out.println(errorMessage.getMessage());
    }
    public ChessGame getChessGame(){
        return chessGame;
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
            WebSocketFacade ws = new WebSocketFacade(serverUrl, this);
            ws.joinObserve(params[1], ChessGame.TeamColor.WHITE, authToken);
            return true;
        } else {
            if (params[2].toUpperCase().equals("WHITE")) {
                game.setPlayerColor("WHITE");
                try {
                    serverFacade.join(game);
                    WebSocketFacade ws = new WebSocketFacade(serverUrl, this);
                    ws.joinUser(params[1], ChessGame.TeamColor.WHITE, authToken);
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
                    WebSocketFacade ws = new WebSocketFacade(serverUrl, this);
                    ws.joinUser(params[1], ChessGame.TeamColor.BLACK, authToken);
                    System.out.println("Joined Game");
                } catch (ResponseException e) {
                    System.out.println("black player already assigned");
                    return false;
                }
            }
        }
        return true;
    }

    public void makeMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotion){
        try {
        WebSocketFacade ws = new WebSocketFacade(serverUrl, this);
        ws.makeMove(startPosition, endPosition, promotion,  authToken);}
        catch (Exception e) {
            System.out.println("invalid");
        }
    }

    public void leave(String id){
        try {
            WebSocketFacade ws = new WebSocketFacade(serverUrl, this);
            ws.leave(id);
        } catch (Exception e){
            System.out.println("invalid");
        }
    }

    public void resign(String id){
        try {
            WebSocketFacade ws = new WebSocketFacade(serverUrl, this);
            ws.resign(id);
        }
        catch (Exception e){
            System.out.println("Error");
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
                    - make move
                    - resign
                    """;
        }
    }

}
