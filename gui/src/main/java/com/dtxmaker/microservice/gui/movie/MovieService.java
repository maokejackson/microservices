package com.dtxmaker.microservice.gui.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class MovieService
{
    private final RestTemplate restTemplate;

    public Movie[] getMovies()
    {
        String url = "http://api-gateway/api/movies";
        return restTemplate.getForObject(url, Movie[].class);
    }

    public Movie getMovie(Long movieId)
    {
        String url = "http://api-gateway/api/movies/{movieId}";
        return restTemplate.getForObject(url, Movie.class, movieId);
    }
}
