package com.moviemania.MovieMania.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.moviemania.MovieMania.models.UserReview;
import com.moviemania.MovieMania.repositories.UserReviewRepository;
import com.moviemania.MovieMania.services.Movie;
import com.moviemania.MovieMania.services.MovieService;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    private final MovieService movieService;
    
    @Autowired
    private UserReviewRepository userReviewRepo;

    public IndexController(MovieService movieService) {
        this.movieService = movieService;
    }
    
    @GetMapping("/api/teste")
    public String sayHello() {
    	return "Oi :)";
    }
	
    @GetMapping("/")
    public String homePage(Model model, HttpSession session) {
        model.addAttribute("userJoinedSession", Boolean.TRUE.equals(session.getAttribute("userJoinedSession")));
        model.addAttribute("isAdmin", Boolean.TRUE.equals(session.getAttribute("isAdmin")));
        return "index";
    }
    
    @GetMapping("/minhas_avaliacoes")
    public String minhasAvaliacoes(Model model, HttpSession session) {
        Boolean loggedIn = Boolean.TRUE.equals(session.getAttribute("userJoinedSession"));
        Integer userId = (Integer) session.getAttribute("userId");

        if (!loggedIn || userId == null) {
            return "redirect:/login";
        }

        List<UserReview> reviews = userReviewRepo.findByUserId(userId);

        model.addAttribute("reviews", reviews);
        model.addAttribute("userJoinedSession", true);
        model.addAttribute("isAdmin", Boolean.TRUE.equals(session.getAttribute("isAdmin")));
        
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
        	reviews.clear();
        }

        return "minhas_avaliacoes"; 
    }

    @PostMapping("/avaliar")
    public String avaliarFilme(@RequestParam("movieId") Long movieId, @RequestParam("rating") int rating, 
    		HttpSession session,RedirectAttributes redirectAttributes) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("erro", "Você precisa estar logado para avaliar um filme.");
            System.out.println("[LOG: USER_ID(" + userId + ") não está autenticado para avaliar.]");
            return "redirect:/login";
        }
        Movie filme = movieService.getMovieById(movieId.intValue());
        if (filme == null) {
            redirectAttributes.addFlashAttribute("erro", "Filme não encontrado.");
            System.out.println("[LOG: USER_ID(" + userId + ") não conseguiu localizar um filme]");
            return "redirect:/movies";
        }

        UserReview reviewExistente = userReviewRepo.findByUserIdAndFilmeId(userId, movieId);
        if (reviewExistente != null) {
            redirectAttributes.addFlashAttribute("erro", "Você já avaliou este filme.");
            System.out.println("[LOG: USER_ID(" + userId + ") tentou avaliar um filme já antes avaliado MovieID(" + movieId + ")]");
            return "redirect:/filme/" + movieId;
        }
        UserReview novaReview = new UserReview();
        novaReview.setUserId(userId);
        novaReview.setFilmeId(movieId);
        novaReview.setNota(rating);
        novaReview.setPosterPath(filme.getPosterPath());
        userReviewRepo.save(novaReview);

        redirectAttributes.addFlashAttribute("mensagem", "Avaliação enviada com sucesso!");
        System.out.println("[LOG: USER_ID(" + userId + ") registrou uma avaliação em MovieID(" + movieId + ") dando a nota(" + rating + "★)]");
        return "redirect:/filme/" + movieId;
    }
    

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/cadastro")
	public String cadastro() {
		return "cadastro";
	}
	
	/*@GetMapping("/admin/ver")
	public String ver(Model model, HttpSession session) {
		boolean usuarioAutenticado = session.getAttribute("isAdmin") != null;
		model.addAttribute("isAdmin", usuarioAutenticado);
			if (usuarioAutenticado == false) {
				return "redirect:/";
			}
		return "/admin/ver";
	}*/
    
    @GetMapping("/filme/{id}")
    public String viewPoster(@PathVariable int id, Model model, HttpSession session) {
        // Busca o filme
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);

        Boolean userJoined = (Boolean) session.getAttribute("userJoinedSession");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        Integer userId = (Integer) session.getAttribute("userId");

        model.addAttribute("userJoinedSession", userJoined != null ? userJoined : false);
        model.addAttribute("isAdmin", isAdmin != null ? isAdmin : false);
        model.addAttribute("userId", userId);

        return "filme";
    }
    
}
//Ao clicar no poster, ele vai para /filme/{id}.