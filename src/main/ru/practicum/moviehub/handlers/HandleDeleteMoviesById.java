package ru.practicum.moviehub.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.practicum.moviehub.api.ErrorResponse;
import ru.practicum.moviehub.store.MoviesStore;
import ru.practicum.moviehub.util.HttpResponseUtils;

import java.io.IOException;

public class HandleDeleteMoviesById extends AbstractHandler{
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

            // Удаляем фильм
            boolean deleted = store.delete(id);
            if (!deleted) {
                ErrorResponse error = new ErrorResponse("Фильм не найден");
                HttpResponseUtils.sendResponse(exchange, 404, GSON.toJson(error));
                return;
            }

            // Возвращаем успешный ответ без тела
        HttpResponseUtils.sendResponse(exchange, 204);
        }
    }

