package com.moviemania.MovieMania.services;

import java.util.List;

public class MovieApiResponse {

    private List<Movie> results;

    public List<Movie> getResults() { return results; }
    public void setResults(List<Movie> results) { this.results = results; }
}
