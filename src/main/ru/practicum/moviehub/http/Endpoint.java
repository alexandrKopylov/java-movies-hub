package ru.practicum.moviehub.http;

public enum Endpoint {
    GET_MOVIES,
    GET_MOVIES_ID,
    GET_MOVIES_YEAR,
    POST_MOVIES,
    DELETE_MOVIES_ID,
    UNKNOWN

    /*
    GET /movies.
    POST /movies
    GET /movies/{id}
    DELETE /movies/{id}
    GET /movies?year=YYYY
     */
}
