package ru.practicum.moviehub.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.practicum.moviehub.api.ErrorResponse;
import ru.practicum.moviehub.model.Movie;
import ru.practicum.moviehub.store.MoviesStore;
import ru.practicum.moviehub.util.HttpResponseUtils;

import java.io.IOException;
import java.util.List;

public class HandleGetMoviesByYear extends AbstractHandler {
    @Override
    public void process(HttpExchange exchange, MoviesStore store) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        List<Movie> movies = null;
        String yearParam = null;
        String[] queryParams = query.split("&");
        for (String param : queryParams) {
            if (param.startsWith("year=")) {
                yearParam = param.substring(5);
                break;
            }
        }

        if (yearParam != null) {
            try {
                int year = Integer.parseInt(yearParam);
                movies = store.getByYear(year);
            } catch (NumberFormatException e) {
                ErrorResponse error = new ErrorResponse("Некорректный параметр запроса - 'year'");
                HttpResponseUtils.sendResponse(exchange, 400, GSON.toJson(error));
                return;
            }
        }
        String moviesJson = GSON.toJson(movies);
        HttpResponseUtils.sendResponse(exchange, 200, moviesJson);
    }
}
