package com.dtxmaker.microservice.resource.movie.core;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api(tags = "Movie")
@RestController
@RequestMapping("/api/movies")
public class MovieController
{
    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    @ApiOperation(value = "Get all movies", response = Movie.class)
    @GetMapping
    public Flux<Movie> getMovies()
    {
        return movieRepository.findAll();
    }

    @ApiOperation(value = "Get a movie", response = Movie.class)
    @GetMapping("/{movieId}")
    public Mono<Movie> getMovie(@PathVariable("movieId") Long movieId)
    {
        return movieRepository.findById(movieId);
    }
}
