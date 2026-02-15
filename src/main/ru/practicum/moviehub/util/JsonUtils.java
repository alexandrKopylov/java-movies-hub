package ru.practicum.moviehub.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonUtils {
    private static final Gson gson = new Gson();

    public static <T> T parseJson(InputStreamReader reader, Class<T> clazz) throws IOException {
        try {
            return gson.fromJson(reader, clazz);
        } catch (JsonSyntaxException e) {
            throw new IOException("Invalid JSON", e);
        }
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
