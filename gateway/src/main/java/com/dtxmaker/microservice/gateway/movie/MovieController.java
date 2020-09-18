package com.dtxmaker.microservice.gateway.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api(tags = "Movie")
@RestController
@RequestMapping("/api/movies")
public class MovieController
{
    private final WebClient webClient;

    public MovieController(WebClient webClient)
    {
        this.webClient = webClient;
    }

    @ApiOperation(value = "Get all movies", response = Movie.class)
    @GetMapping
    public Flux<Movie> getMovies(@AuthenticationPrincipal Jwt jwt)
    {
        return webClient.get()
                .uri("lb://movie-service/api/movies")
                .headers(headers -> headers.setBearerAuth(jwt.getTokenValue()))
                .retrieve().bodyToFlux(Movie.class)
                .flatMap(movie -> getReviews(movie.getId(), jwt)
                        .map(review -> {
                            movie.addReview(review);
                            return movie;
                        })
                        .reduce((movie1, movie2) -> movie2)
                );
    }

    @ApiOperation(value = "Get a movie", response = Movie.class)
    @GetMapping("/{movieId}")
    public Mono<Movie> getMovie(@PathVariable("movieId") Long movieId, @AuthenticationPrincipal Jwt jwt)
    {
        Mono<Movie> movie = webClient.get()
                .uri("lb://movie-service/api/movies/{movieId}", movieId)
                .headers(headers -> headers.setBearerAuth(jwt.getTokenValue()))
                .retrieve().bodyToMono(Movie.class);

        Flux<MovieReview> reviews = getReviews(movieId, jwt);

        return Mono.zip(movie, reviews.collectList(), Movie::new);
    }

    private Flux<MovieReview> getReviews(Long movieId, Jwt jwt)
    {
        return webClient.get()
                .uri("lb://review-service/api/reviews?movieId={movieId}", movieId)
                .headers(headers -> headers.setBearerAuth(jwt.getTokenValue()))
                .retrieve().bodyToFlux(MovieReview.class);
    }
}
