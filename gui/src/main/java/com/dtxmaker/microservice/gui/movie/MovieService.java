package com.dtxmaker.microservice.gui.movie;

import com.dtxmaker.microservice.gui.config.DefaultRestTemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieService
{
    private final DefaultRestTemplate restTemplate;

    public Movie[] getMovies()
    {
        String url = "/api/movies";
        return restTemplate.get(url, Movie[].class);
    }

    public Movie getMovie(Long movieId)
    {
        String url = "/api/movies/{movieId}";
        return restTemplate.get(url, Movie.class, movieId);
    }
}
