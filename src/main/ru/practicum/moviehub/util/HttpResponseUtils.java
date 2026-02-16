package ru.practicum.moviehub.util;



import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.practicum.moviehub.api.ErrorResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponseUtils {
    private static final Gson GSON = new Gson().newBuilder().create();

    public static void sendResponse(
            HttpExchange exchange,
            int statusCode,
            Object responseBody
    ) throws IOException {
        String responseJson = GSON.toJson(responseBody);
        byte[] responseBytes = responseJson.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    public static void sendError(
            HttpExchange exchange,
            int statusCode,
            String message
    ) throws IOException {
        sendResponse(exchange, statusCode, new ErrorResponse(message));
    }
}
