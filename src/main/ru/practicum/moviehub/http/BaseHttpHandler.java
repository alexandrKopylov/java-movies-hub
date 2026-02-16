package ru.practicum.moviehub.http;

import com.sun.net.httpserver.HttpHandler;
import ru.practicum.moviehub.store.MoviesStore;

public abstract class BaseHttpHandler implements HttpHandler {
   protected MoviesStore store;
    public BaseHttpHandler(MoviesStore store) {
        this.store = store;
    }
}