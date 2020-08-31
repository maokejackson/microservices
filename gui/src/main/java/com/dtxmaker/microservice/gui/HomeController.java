package com.dtxmaker.microservice.gui;

import com.dtxmaker.microservice.gui.movie.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController
{
    private final HttpServletRequest request;
    private final MovieService       movieService;

    @Autowired
    public HomeController(HttpServletRequest request, MovieService movieService)
    {
        this.request = request;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/login")
    public String login()
    {
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(Principal principal) throws ServletException
    {
        if (principal == null)
        {
            return "redirect:/";
        }
        else
        {
            request.logout();
            return "logout";
        }
    }
}
