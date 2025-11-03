package com.moviemania.MovieMania.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviemania.MovieMania.models.UserModel;
import com.moviemania.MovieMania.repositories.UserRepository;
import com.moviemania.MovieMania.repositories.UserReviewRepository;

@Service
public class UserService {

    private final UserRepository userRepo;
    @Autowired
    private UserReviewRepository userReviewRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserModel saveUser(UserModel user) {
        return userRepo.save(user);
    }
    
    public Optional<UserModel> findById(Integer userId) {
        return userRepo.findById(userId);
    }

    public UserModel findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Transactional
    public void addFilmeToWishlist(Integer ID, Integer userId) {
        userRepo.addFilmeToWishlist(ID, userId);
    }
    
    @Transactional
    public boolean deleteUserAndData(Integer userId) {

        userReviewRepo.deleteByUserId(userId);

        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
            return true;
        }

        return false;
    }

/*
    @Transactional
    public void addFilmeToWishlist(Integer userId, String idFilme) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            if (!user.getListaDesejoId().contains(idFilme)) {
                user.getListaDesejoId().add(idFilme);
                userRepository.save(user); // JPA vai gerenciar a inserção na tabela de junção
            }
        }
    }

    @Transactional
    public void removeFilmeFromWishlist(Integer userId, String idFilme) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            user.getListaDesejoId().remove(idFilme);
            userRepository.save(user); // JPA vai gerenciar a remoção da tabela de junção
        }
    }*/
}