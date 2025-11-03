package com.moviemania.MovieMania.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moviemania.MovieMania.models.AdminModel;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel, Integer> {

    boolean existsByEmail(String email);
    
    AdminModel findByEmail(String email);

    @Query("SELECT a FROM AdminModel a WHERE a.email = :email AND a.senha = :senha")
    AdminModel login(@Param("email") String email, @Param("senha") String senha);

    @Modifying
    @Transactional
    @Query(value = "UPDATE admin_model SET lista_desejo_id = " +
                   "CASE WHEN :idFilme MEMBER OF lista_desejo_id THEN lista_desejo_id ELSE CONCAT(lista_desejo_id, ',', :idFilme) END " +
                   "WHERE id = :adminId", nativeQuery = true)
    void addFilmeToWishlist(@Param("idFilme") String idFilme, @Param("adminId") Integer adminId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM admin_lista_desejo_id WHERE admin_model_id = :adminId AND lista_desejo_id = :idFilme", nativeQuery = true)
    void removeFilmeFromWishlist(@Param("idFilme") String idFilme, @Param("adminId") Integer adminId);
}
