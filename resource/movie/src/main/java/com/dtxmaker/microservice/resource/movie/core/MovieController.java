package com.dtxmaker.microservice.resource.movie.core;

import com.dtxmaker.microservice.resource.movie.feign.ReviewsFeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/movies")
public class MovieController
{
    private final MovieRepository    movieRepository;
    private final ReviewsFeignClient reviewsFeignClient;

    public MovieController(MovieRepository movieRepository, ReviewsFeignClient reviewsFeignClient)
    {
        this.movieRepository = movieRepository;
        this.reviewsFeignClient = reviewsFeignClient;
    }

    @GetMapping
    public List<MovieDTO> getMovies()
    {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> new MovieDTO(movie, reviewsFeignClient.getMovieReviews(movie.getId())))
                .collect(Collectors.toList());
    }

    @GetMapping("/{movieId}")
    public MovieDTO getMovie(@PathVariable("movieId") Long movieId)
    {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        return new MovieDTO(movie, reviewsFeignClient.getMovieReviews(movieId));
    }
}
