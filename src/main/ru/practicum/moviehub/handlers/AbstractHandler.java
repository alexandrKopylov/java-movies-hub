package ru.practicum.moviehub.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import ru.practicum.moviehub.store.MoviesStore;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class AbstractHandler {
    public static final Gson GSON = new GsonBuilder().create();
    abstract public void process(HttpExchange exchange, MoviesStore store) throws IOException;
}
