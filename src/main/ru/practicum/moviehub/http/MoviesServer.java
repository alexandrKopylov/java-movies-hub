package ru.practicum.moviehub.http;

import com.sun.net.httpserver.HttpServer;
import ru.practicum.moviehub.store.MoviesStore;

import java.io.IOException;
import java.net.InetSocketAddress;


public class MoviesServer {
    private HttpServer server = null;
    private MoviesStore store;

    public MoviesServer(MoviesStore moviesStore, int port) {
        this.store = moviesStore;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/movies", new MoviesHandler(store));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        server.stop(0);
    }

    public void start() {
        server.start();
    }
}