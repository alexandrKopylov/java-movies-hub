package ru.practicum.moviehub.http;

import com.sun.net.httpserver.HttpExchange;
import ru.practicum.moviehub.handlers.*;
import ru.practicum.moviehub.store.MoviesStore;
import ru.practicum.moviehub.util.HttpResponseUtils;

import java.io.IOException;
import java.util.Objects;

public class MoviesHandler extends BaseHttpHandler {
    private final HandleGetMovies handleGetMovies = new HandleGetMovies();
    private final HandlePostMovies handlePostMovies = new HandlePostMovies();
    private final HandleDeleteMoviesById handleDeleteMoviesById = new HandleDeleteMoviesById();
    private final HandleGetMoviesById handleGetMoviesById = new HandleGetMoviesById();
    private final HandleGetMoviesByYear handleGetMoviesByYear = new HandleGetMoviesByYear();

    public MoviesHandler(MoviesStore store) {
        super(store);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();
        String requestMethod = exchange.getRequestMethod();
        Endpoint endpoint = getEndpoint(requestPath, requestMethod, query);

        switch (endpoint) {
            case GET_MOVIES -> handleGetMovies.process(exchange, store);
            case GET_MOVIES_ID -> handleGetMoviesById.process(exchange, store);
            case GET_MOVIES_YEAR -> handleGetMoviesByYear.process(exchange, store);
            case POST_MOVIES -> handlePostMovies.process(exchange, store);
            case DELETE_MOVIES_ID -> handleDeleteMoviesById.process(exchange, store);
            default -> HttpResponseUtils.sendError(exchange, 405, "Метод не поддерживается");
        }
    }

    private Endpoint getEndpoint(String requestPath, String requestMethod, String query) {
        String[] pathParts = requestPath.split("/");
        if (pathParts.length == 2 &&
                requestMethod.equals("GET") &&
                (Objects.nonNull(query) && query.toUpperCase().startsWith("YEAR="))) {
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
}



