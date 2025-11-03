package com.moviemania.MovieMania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moviemania.MovieMania.models.AdminModel;
import com.moviemania.MovieMania.models.UserModel;
import com.moviemania.MovieMania.services.LoginService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    private static final String WRONG_PASSWORD = "Email ou senha incorreta!";
    private static final String USER_NOT_FOUND = "Usuário não cadastrado!";

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String senha,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        UserModel user = loginService.getUserModelByEmail(email);
        if (user != null) {
            if (!user.getSenha().equals(senha)) {
                return redirectWithError(WRONG_PASSWORD, redirectAttributes);
            }
            session.setAttribute("userJoinedSession", true);
            session.setAttribute("isAdmin", false);
            session.setAttribute("userId", user.getID());
            return "redirect:/";
        }

        AdminModel admin = loginService.getAdmModelByEmail(email);
        if (admin != null) {
            if (!admin.getSenha().equals(senha)) {
                return redirectWithError(WRONG_PASSWORD, redirectAttributes);
            }
            session.setAttribute("userJoinedSession", true);
            session.setAttribute("isAdmin", true);
            session.setAttribute("userId", admin.getID());
            return "redirect:admin/ver";
        }

        return redirectWithError(USER_NOT_FOUND, redirectAttributes);
    }


    private String redirectWithError(String message, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", message);
        return "redirect:/login";
    }
}
