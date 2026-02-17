package ru.practicum.moviehub.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.practicum.moviehub.api.ErrorResponse;
import ru.practicum.moviehub.model.Movie;
import ru.practicum.moviehub.store.MoviesStore;
import ru.practicum.moviehub.util.HttpResponseUtils;

import java.io.IOException;
import java.util.Optional;

public class HandleGetMoviesById extends AbstractHandler{
    @Override
    public void process(HttpExchange exchange, MoviesStore store) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");

        // Проверяем формат пути: /movies/{id}
        if (pathParts.length != 3) {
            ErrorResponse error = new ErrorResponse("Не найдено");
            HttpResponseUtils.sendResponse(exchange, 404, GSON.toJson(error));
            return;
        }

        String idParam = pathParts[2];

        // Проверяем, что ID - число
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            ErrorResponse error = new ErrorResponse("Некорректный ID");
            HttpResponseUtils.sendResponse(exchange, 400, GSON.toJson(error));
            return;
        }

        // Ищем фильм
       Optional<Movie> movieOptional = store.getById(id);
        if (movieOptional.isPresent()) {
            // Возвращаем фильм
            String response = GSON.toJson(movieOptional.get());
            HttpResponseUtils.sendResponse(exchange, 200, response);
        }else {
            ErrorResponse error = new ErrorResponse("Фильм не найден");
            HttpResponseUtils.sendResponse(exchange, 404, GSON.toJson(error));
        }


    }
}
