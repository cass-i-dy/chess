package server;

import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthToken;
import model.Game;
import model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {

    public AuthToken auth = null;
    public String serverUrl;
    public ServerFacade(String url) {
        serverUrl = url;
    }

    public void register(User message) throws ResponseException {
        var path = "/user";
        auth =  this.makeRequest("POST", path, message, AuthToken.class);
    }
    public void login(User message) throws ResponseException {
        var path = "/session";
        auth =  this.makeRequest("POST", path, message, AuthToken.class);
    }
    public void logout() throws ResponseException {
        var path = "/session";
        this.makeRequest("DELETE", path, null, null);
    }
    public Game create(Game message) throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, message, Game.class);
    }
    public Game[] list() throws ResponseException {
        var path = "/game";
        record listGameResponse(Game[] game) {
        }
        var response = this.makeRequest("GET", path, null, listGameResponse.class);
        return response.game();
    }

    public void join(Game message) throws ResponseException {
        var path = "/game";
        this.makeRequest("PUT", path, message, Game.class);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            if (!(auth==null)) {
                http.setRequestProperty("Authorization", auth.getToken());
            }
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
//            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
