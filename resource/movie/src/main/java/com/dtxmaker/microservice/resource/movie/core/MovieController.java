package com.dtxmaker.microservice.resource.movie.core;

import com.dtxmaker.microservice.resource.movie.feign.ReviewsFeignClient;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/movies")
public class MovieController
{
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final MovieRepository    movieRepository;
    private final ReviewsFeignClient reviewsFeignClient;

    public MovieController(MovieRepository movieRepository, ReviewsFeignClient reviewsFeignClient)
    {
        this.movieRepository = movieRepository;
        this.reviewsFeignClient = reviewsFeignClient;
    }

    @GetMapping
    public List<MovieDTO> getMovies(@RequestHeader(AUTHORIZATION_HEADER) String authHeader)
    {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> new MovieDTO(movie, reviewsFeignClient.getMovieReviews(authHeader, movie.getId())))
                .collect(Collectors.toList());
    }

    @GetMapping("/{movieId}")
    public MovieDTO getMovie(@RequestHeader(AUTHORIZATION_HEADER) String authHeader,
            @PathVariable("movieId") Long movieId)
    {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        return new MovieDTO(movie, reviewsFeignClient.getMovieReviews(authHeader, movieId));
    }
}
