package com.moviemania.MovieMania.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moviemania.MovieMania.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_model (nome_completo, email, senha) " +
                   "VALUES (:nome_completo, :email, :senha)", nativeQuery = true)
    void cadastrarUser(
            @Param("nome_completo") String nome_completo, 
            @Param("email") String email,
            @Param("senha") String senha
    );


    UserModel findByEmail(String email);
    
    Optional<UserModel> findById(Integer id);


    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserModel u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);


    @Query("SELECT u FROM UserModel u WHERE u.email = :email AND u.senha = :senha")
    UserModel login(@Param("email") String email, @Param("senha") String senha);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO wishlist (user_id, filme_id) VALUES (:userId, :filmeId)", nativeQuery = true)
    void addFilmeToWishlist(@Param("userId") Integer userId, @Param("filmeId") Integer filmeId);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_review (user_id, filme_id, nota) VALUES (:userId, :filmeId, :nota)", nativeQuery = true)
    void addNotaFilme(@Param("userId") Integer userId, @Param("filmeId") Integer filmeId, @Param("nota") Integer nota);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_model_lista_desejo_id WHERE user_model_id = :userId AND lista_desejo_id = :filmeId", nativeQuery = true)
    void removeFilmeFromWishlist(@Param("filmeId") Integer filmeId, @Param("userId") Integer userId);
}
