package com.dtxmaker.microservice.gui;

import com.dtxmaker.microservice.gui.movie.MovieService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController
{
    private final MovieService movieService;

    public HomeController(MovieService movieService)
    {
        this.movieService = movieService;
    }

    @GetMapping
    public String home()
    {
        return "index";
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @GetMapping("/movies")
    public String movies(Model model, Principal principal)
    {
        model.addAttribute("name", principal.getName());
        model.addAttribute("movies", movieService.getMovies());
        return "movies";
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("/manage")
    public String manage(Model model, Principal principal)
    {
        model.addAttribute("name", principal.getName());
        return "manage";
    }
}
