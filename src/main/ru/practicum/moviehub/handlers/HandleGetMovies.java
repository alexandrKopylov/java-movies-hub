package ru.practicum.moviehub.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.practicum.moviehub.model.Movie;
import ru.practicum.moviehub.store.MoviesStore;
import ru.practicum.moviehub.util.HttpResponseUtils;

import java.io.IOException;
import java.util.List;

public class HandleGetMovies extends AbstractHandler{
    public void process(HttpExchange exchange, MoviesStore store) throws IOException {
        List<Movie> movies = store.getAll();
        String moviesJson = GSON.toJson(movies);
        HttpResponseUtils.sendResponse(exchange, 200, movies);
    }
}