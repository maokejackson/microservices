package com.dtxmaker.microservice.resource.movie;

import com.dtxmaker.microservice.resource.movie.feign.ReviewsFeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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
        Iterator<Movie> movies = movieRepository.findAll().iterator();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(movies, Spliterator.ORDERED), false)
                .map(movie -> {
                    CollectionModel<MovieReview> movieReviews = reviewsFeignClient.getMovieReviews(movie.getId());
                    return new MovieDTO(movie, movieReviews.getContent());
                }).collect(Collectors.toList());
    }

    @GetMapping("/{movieID}")
    public MovieDTO getMovie(@PathVariable("movieID") Long movieId)
    {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        CollectionModel<MovieReview> movieReviews = reviewsFeignClient.getMovieReviews(movieId);
        return new MovieDTO(movie, movieReviews.getContent());
    }
}
