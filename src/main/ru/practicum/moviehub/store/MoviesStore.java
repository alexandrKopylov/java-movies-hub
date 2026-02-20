package ru.practicum.moviehub.store;

import ru.practicum.moviehub.model.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class MoviesStore {
    private static int idIndex = 0;
    private final Map<Integer, Movie> store = new HashMap<>();

    public Movie add(Movie movie) {
        movie.setId(++idIndex);
        store.put(idIndex, movie);
        return movie;
    }

    public Optional<Movie> getById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Movie> getAll() {
        return new ArrayList<>(store.values());
    }

    public List<Movie> getByYear(int year) {
        return store.values().stream()
                .filter(m -> m.getYear() == year)
                .collect(Collectors.toList());
    }

    public boolean delete(int id) {
        return store.remove(id) != null;
    }
}