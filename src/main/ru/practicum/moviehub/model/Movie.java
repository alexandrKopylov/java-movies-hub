package ru.practicum.moviehub.model;

import java.util.Objects;

public class Movie {
    static private int idIndex = 0;
    private final int id;
    private final String title;
    private final int year;


    public Movie( String title, int year) {
        this.id = ++idIndex;
        this.title = title;
        this.year = year;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && year == movie.year && Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(id).append(" ").append(year).append(" ").append(title).append("}");
        return sb.toString();
    }
}