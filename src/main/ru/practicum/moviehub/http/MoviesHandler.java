package ru.practicum.moviehub.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MoviesHandler extends BaseHttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();
        String requestMethod = exchange.getRequestMethod();
        Endpoint endpoint = getEndpoint(requestPath, requestMethod, query);

        switch (endpoint) {
            case GET_MOVIES -> handleGetMovies(exchange);
            case GET_MOVIES_ID -> handleGetMoviesById(exchange);
            case GET_MOVIES_YEAR -> handleGetMoviesByYear(exchange);
            case POST_MOVIES -> handlePostMovies(exchange);
            case DELETE_MOVIES_ID -> handleDeleteMoviesById(exchange);
            default -> writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }

    private void handleDeleteMoviesById(HttpExchange exchange) throws IOException {

    }

    private void handlePostMovies(HttpExchange exchange) throws IOException {

    }

    private void handleGetMoviesByYear(HttpExchange exchange) throws IOException {

    }

    private void handleGetMoviesById(HttpExchange exchange) throws IOException {

    }

    private void handleGetMovies(HttpExchange exchange) throws IOException {


    }

    private Endpoint getEndpoint(String requestPath, String requestMethod, String query) {
        String[] pathParts = requestPath.split("/");
        if (pathParts.length == 2 &&
                requestMethod.equals("GET") &&
                query.toUpperCase().startsWith("YEAR=")) {
            return Endpoint.GET_MOVIES_YEAR;
        } else if (pathParts.length == 2 && requestMethod.equals("POST")) {
            return Endpoint.POST_MOVIES;
        } else if (pathParts.length == 3 && requestMethod.equals("DELETE")) {
            return Endpoint.DELETE_MOVIES_ID;
        } else if (pathParts.length == 3 && requestMethod.equals("GET")) {
            return Endpoint.GET_MOVIES_ID;
        } else if (pathParts.length == 2 && requestMethod.equals("GET")) {
            return Endpoint.GET_MOVIES;
        }
        return Endpoint.UNKNOWN;
    }

    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {

        exchange.sendResponseHeaders(responseCode, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseString.getBytes());
        }
    }


}
