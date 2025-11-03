package com.moviemania.MovieMania.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviemania.MovieMania.repositories.UserReviewRepository;

@Service
public class MovieVoteService {

    @Autowired
    private UserReviewRepository userReviewRepository;

    public List<Movie> enrichMoviesWithUserVotes(List<Movie> movies) {
        for (Movie movie : movies) {
            int apiVoteCount = movie.getVoteCount();
            int userVotes = userReviewRepository.countByFilmeId((long) movie.getId()); 
            movie.setVoteCount(apiVoteCount + (userVotes * 100)); // cada voto vale por 100 pra ser melhor ver
        }
        return movies;
    }
}

