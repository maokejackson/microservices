package com.dtxmaker.microservice.gui.movie;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MovieService
{
    private final WebClient webClient;

    @Autowired
    public MovieService(WebClient webClient)
    {
        this.webClient = webClient;
    }

    public Movie[] getMovies()
    {
        return webClient.get()
                .uri("http://localhost:8084/movie-service/api/movies")
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .bodyToMono(Movie[].class)
                .block();
//        String url = "http://localhost:8084/movie-service/api/movies";
//        return restTemplateHelper.get(url, Movie[].class);
    }

    public Movie getMovie(Long movieId)
    {
        return webClient.get()
                .uri("http://localhost:8084/movie-service/api/movies/{movieId}", movieId)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
//        String url = "http://localhost:8084/movie-service/api/movies/{movieId}";
//        return restTemplateHelper.get(url, Movie.class, movieId);
    }
}
