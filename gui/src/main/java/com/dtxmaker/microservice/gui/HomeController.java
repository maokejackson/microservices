package com.dtxmaker.microservice.gui;

import com.dtxmaker.microservice.gui.movie.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    private final MovieService movieService;

    @Autowired
    public HomeController(MovieService movieService)
    {
        this.movieService = movieService;
    }

    @GetMapping
    public String home(Model model)
    {
        model.addAttribute("movies", movieService.getMovies());
        return "index";
    }
}
