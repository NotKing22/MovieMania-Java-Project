package com.moviemania.MovieMania.controller;

import com.moviemania.MovieMania.dto.ReviewDTO;
import com.moviemania.MovieMania.models.UserModel;
import com.moviemania.MovieMania.models.UserReview;
import com.moviemania.MovieMania.repositories.UserRepository;
import com.moviemania.MovieMania.repositories.UserReviewRepository;
import com.moviemania.MovieMania.services.Movie;
import com.moviemania.MovieMania.services.MovieService;
import com.moviemania.MovieMania.services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserReviewRepository userReviewRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/reviews/{id}")
    public String verReviewsUsuario(@PathVariable Integer id, Model model, HttpSession session,
    		@RequestParam(required = false, defaultValue = "desc") String sort) {
        boolean usuarioAutenticado = session.getAttribute("isAdmin") != null;
        if (!usuarioAutenticado) {
            return "redirect:/";
        }

        // buscar usuário
        Optional<UserModel> usuarioOpt = userRepo.findById(id);
        if (usuarioOpt.isEmpty()) {
            return "redirect:admin";
        }
        UserModel usuario = usuarioOpt.get();
        model.addAttribute("usuario", usuario);

        List<UserReview> reviews = userReviewRepo.findByUserId(id.intValue());
        
        if (sort.equalsIgnoreCase("asc")) {
            reviews.sort(Comparator.comparingInt(UserReview::getNota));
        } else {
            reviews.sort(Comparator.comparingInt(UserReview::getNota).reversed());
        }

        List<ReviewDTO> filmes = new ArrayList<>();
        for (UserReview review : reviews) {
            Movie movie = movieService.getMovieById(review.getFilmeId().intValue());
            String title;
            if (movie != null && movie.getTitle() != null && !movie.getTitle().isBlank()) {
                title = movie.getTitle();
            } else {
                title = "Filme #" + review.getFilmeId(); // fallback se API não retornar
            }
            filmes.add(new ReviewDTO(title, review.getNota()));
        }

        model.addAttribute("filmes", filmes);
        model.addAttribute("reviewsCount", filmes.size()); 
        model.addAttribute("usuario", usuario);
        model.addAttribute("reviews", reviews);
        model.addAttribute("sort", sort);
        return "admin/reviews"; 
    }

    @PostMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable Integer id,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {

        boolean isAdmin = Boolean.TRUE.equals(session.getAttribute("isAdmin"));
        if (!isAdmin) {
            return "redirect:login";
        }

        boolean deleted = userService.deleteUserAndData(id);

        if (deleted) {
            redirectAttributes.addFlashAttribute("mensagem",
                    "Usuário e todos os dados associados foram apagados com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagem",
                    "Nenhum registro encontrado para esse usuário (mas as reviews foram limpas).");
        }

        return "redirect:/admin/ver";
    }
    
    
    
    @GetMapping("/ver")
    public String dashboard(Model model, HttpSession session) {
        boolean usuarioAutenticado = session.getAttribute("isAdmin") != null;
        if (!usuarioAutenticado) {
            return "redirect:/";
        }
        List<UserModel> usuarios = userRepo.findAll();
        model.addAttribute("usuarios", usuarios);
        return "admin/ver"; // lista de usuários
    }
}



