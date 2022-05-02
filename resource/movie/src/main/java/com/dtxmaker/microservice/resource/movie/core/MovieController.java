package com.dtxmaker.microservice.resource.movie.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/movies")
public class MovieController
{
    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public Flux<Movie> getMovies()
    {
        return movieRepository.findAll();
    }

    @GetMapping("/{movieId}")
    public Mono<Movie> getMovie(@PathVariable("movieId") Long movieId)
    {
        return movieRepository.findById(movieId);
    }
}
