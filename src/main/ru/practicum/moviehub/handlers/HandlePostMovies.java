package ru.practicum.moviehub.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.practicum.moviehub.api.ErrorResponse;
import ru.practicum.moviehub.model.Movie;
import ru.practicum.moviehub.store.MoviesStore;
import ru.practicum.moviehub.util.HttpResponseUtils;
import ru.practicum.moviehub.validator.MovieValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HandlePostMovies extends AbstractHandler{
    @Override
    public void process(HttpExchange exchange, MoviesStore store) throws IOException {
        // Проверяем Content-Type
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        if (contentType == null || !contentType.contains("application/json")) {
            ErrorResponse error = new ErrorResponse("Unsupported Media Type");
            HttpResponseUtils.sendResponse(exchange, 415, GSON.toJson(error));
            return;
        }

        // Читаем тело запроса
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        try {
            // Парсим JSON
            Movie movie = GSON.fromJson(requestBody.toString(), Movie.class);

            // Валидация
            List<String> validationErrors = MovieValidator.validate(movie);
            if (!validationErrors.isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Ошибка валидации", validationErrors);
                HttpResponseUtils.sendResponse(exchange, 422, GSON.toJson(errorResponse));
                return;
            }

            // Сохраняем фильм
            Movie createdMovie = store.add(movie);

            // Возвращаем созданный фильм
            String response = GSON.toJson(createdMovie);
            HttpResponseUtils.sendResponse(exchange, 201, response);

        } catch (Exception e) {
            // Некорректный JSON
            ErrorResponse error = new ErrorResponse("Некорректный JSON");
            HttpResponseUtils.sendResponse(exchange, 400, GSON.toJson(error));
        }


    }
}
