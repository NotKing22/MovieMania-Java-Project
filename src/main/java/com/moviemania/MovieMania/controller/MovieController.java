package com.moviemania.MovieMania.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviemania.MovieMania.services.Movie;
import com.moviemania.MovieMania.services.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/popular")
    public List<Movie> getPopularMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) String decade,
            @RequestParam(required = false) String genre) {
        
        return movieService.getPopularMovies(page, rating, decade, genre);
    }
    
    @GetMapping("/filme/{id}")
    public String getMovieDetails(@PathVariable int id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movie-details"; // nome do template Thymeleaf
    }

}
