package ui;

import exception.ResponseException;
import model.AuthToken;
import server.ServerFacade;

import java.util.Arrays;

public class ServerClient {
    private String visitorName = null;
    private final ServerFacade server;

    private final String serverUrl;

    private AuthToken auth;



    public ServerClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.auth = null;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register"->register(params);
                case "logout"->logout(params);
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 3) {
            return String.valueOf(server.register(Arrays.toString(params)));
        }
        throw new ResponseException(400, "Expected: <yourname>");
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 2) {
            return String.valueOf(server.login(Arrays.toString(params)));
        }
        throw new ResponseException(400, "Expected: <yourname>");
    }

    public String logout(String... params) throws ResponseException {
        if (params.length >= 1) {
            return server.logout(Arrays.toString(params)).toString();
        }
        throw new ResponseException(400, "Expected");
    }

    public String help() {
        if (auth == null) {
            return """
                    - Register
                    - login <yourname>
                    - quit
                    """;
        }
        return """
                - CreateGame
                - ListGame
                - JoinGame
                - logout
                """;
    }
}
