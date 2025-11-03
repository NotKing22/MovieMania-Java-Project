package com.moviemania.MovieMania.services;

import com.moviemania.MovieMania.repositories.UserReviewRepository;

public class ReviewService {

	private UserReviewRepository userReviewRepo;
	
    public void deleteReviewsByUserId(Integer userId) {
    	userReviewRepo.deleteByUserId(userId);
    }
}
