package com.dtxmaker.microservice.gui.movie;

import com.dtxmaker.microservice.gui.config.RestTemplateHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService
{
    private final RestTemplateHelper restTemplateHelper;

    @Autowired
    public MovieService(RestTemplateHelper restTemplateHelper)
    {
        this.restTemplateHelper = restTemplateHelper;
    }

    public Movie[] getMovies()
    {
        String url = "http://localhost:8084/movie-service/api/movies";
        return restTemplateHelper.get(url, Movie[].class);
    }

    public Movie getMovie(Long movieId)
    {
        String url = "http://localhost:8084/movie-service/api/movies/{movieId}";
        return restTemplateHelper.get(url, Movie.class, movieId);
    }
}
