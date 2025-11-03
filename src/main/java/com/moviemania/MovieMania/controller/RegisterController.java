package com.moviemania.MovieMania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moviemania.MovieMania.models.UserModel;
import com.moviemania.MovieMania.services.LoginService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @Transactional
    @PostMapping("/cadastrado")
    public String cadastro(UserModel userModel, @RequestParam("re_senha") String reSenha, 
    		RedirectAttributes redirectAttributes,  HttpSession session) {
        if (userModel.getSenha() == null || userModel.getSenha().length() < 8) {
            redirectAttributes.addFlashAttribute(
                    "erroSenha",
                    "* A senha deve ter pelo menos 8 caracteres."
            );
            return "redirect:cadastro";
        }

        // Checagem se as senhas são iguais
        if (!userModel.getSenha().equals(reSenha)) {
            redirectAttributes.addFlashAttribute(
                    "erroSenhaConfirmacao",
                    "* As senhas não coincidem."
            );
            return "redirect:cadastro";
        }

        if (!loginService.loginExistsByEmail(userModel.getEmail())) {
            UserModel savedUser = loginService.saveUser(
                    userModel.getNome_completo(),
                    userModel.getEmail(),
                    userModel.getSenha()
            );

            session.setAttribute("usuarioLogado", savedUser);

            return "redirect:/"; 
        } else {
            redirectAttributes.addFlashAttribute(
                    "erroEmail",
                    "* Usuário já cadastrado. Por favor, autentique-se."
            );
            return "redirect:cadastro";
        }
    }


}

