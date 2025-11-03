package com.moviemania.MovieMania.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moviemania.MovieMania.models.UserReview;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

    @Query("SELECT COUNT(u) FROM UserReview u WHERE u.filmeId = :filmeId")
    int countByFilmeId(Long filmeId);

    List<UserReview> findByUserId(Integer userId); 
    
    UserReview findByUserIdAndFilmeId(Integer userId, Long filmeId);
    
    void deleteByUserId(Integer userId);
    
    

}


