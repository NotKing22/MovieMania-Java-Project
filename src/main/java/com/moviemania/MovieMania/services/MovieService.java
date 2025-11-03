package com.moviemania.MovieMania.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MovieService {

    private final WebClient webClient;
    private static final String API_KEY = "YOUR_APY_KEY";

    private final MovieVoteService movieVoteService;

    @Autowired
    public MovieService(WebClient.Builder webClientBuilder, MovieVoteService movieVoteService) {
        this.webClient = webClientBuilder.baseUrl("https://api.themoviedb.org/3").build();
        this.movieVoteService = movieVoteService;
    }

    public List<Movie> getPopularMovies(int page, String rating, String decade, String genre) {
        // Chamada à API com filtros
        Mono<MovieApiResponse> responseMono = this.webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/discover/movie")
                            .queryParam("api_key", API_KEY)
                            .queryParam("language", "pt-BR")
                            .queryParam("page", page);

                    if (rating != null && !rating.isEmpty()) {
                        try {
                            double minRating = Double.parseDouble(rating);
                            uriBuilder.queryParam("vote_average.gte", minRating);
                        } catch (NumberFormatException e) {
                            // Ignora se não for número
                        }
                    }

                    if (decade != null && !decade.isEmpty()) {
                        int startYear = Integer.parseInt(decade);
                        int endYear = startYear + 9;
                        uriBuilder.queryParam("primary_release_date.gte", startYear + "-01-01")
                                .queryParam("primary_release_date.lte", endYear + "-12-31");
                    }

                    if (genre != null && !genre.isEmpty()) {
                        String genreId = getGenreId(genre);
                        if (genreId != null) {
                            uriBuilder.queryParam("with_genres", genreId);
                        }
                    }

                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(MovieApiResponse.class);

        MovieApiResponse response = responseMono.block();

        List<Movie> movies = response != null ? response.getResults() : List.of();

        movies = movieVoteService.enrichMoviesWithUserVotes(movies);

        return movies;
    }

    public Movie getMovieById(int id) {
        Mono<Movie> movieMono = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{id}")
                        .queryParam("api_key", API_KEY)
                        .queryParam("language", "pt-BR")
                        .build(id))
                .retrieve()
                .bodyToMono(Movie.class);

        Movie movie = movieMono.block();

        if (movie != null) {
            // Soma votos do banco para este filme específico
            movieVoteService.enrichMoviesWithUserVotes(List.of(movie));
        }

        return movie;
    }

    private String getGenreId(String genreName) {
        switch (genreName) {
            case "Action": return "28";
            case "Comedy": return "35";
            case "Drama": return "18";
            default: return null;
        }
    }
}
