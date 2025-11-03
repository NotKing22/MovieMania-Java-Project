package com.moviemania.MovieMania.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviemania.MovieMania.models.AdminModel;
import com.moviemania.MovieMania.models.UserModel;
import com.moviemania.MovieMania.repositories.AdminRepository;
import com.moviemania.MovieMania.repositories.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AdminRepository adminRepo;

    public boolean loginExistsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public boolean loginAdmExistsByEmail(String email) {
        return adminRepo.existsByEmail(email);
    }
    
    public UserModel getUserModelByEmail(String email) {
    	return userRepo.findByEmail(email);
    }
    
    public AdminModel getAdmModelByEmail(String email) {
    	return adminRepo.findByEmail(email);
    }

    @Transactional
    public UserModel saveUser(String nomeCompleto, String email, String senha) {
        UserModel user = new UserModel();
        user.setNome_completo(nomeCompleto);
        user.setEmail(email);
        user.setSenha(senha);
        return userRepo.save(user); // salva e retorna a entidade
    }
    
    @Transactional
    public AdminModel saveAdmin(String nomeCompleto, String email, String senha, String cargo) {
        AdminModel admin = new AdminModel();
        admin.setNome_completo(nomeCompleto);
        admin.setEmail(email);
        admin.setSenha(senha);
        admin.setCargo(cargo);
        return adminRepo.save(admin);
    }

}
