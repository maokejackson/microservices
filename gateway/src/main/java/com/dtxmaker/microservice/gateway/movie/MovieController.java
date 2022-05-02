package com.dtxmaker.microservice.gateway.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MovieController
{
    private final WebClient.Builder webClient;

    @GetMapping
    public Flux<Movie> getMovies()
    {
        return webClient.build().get()
                .uri("lb://movie-service/api/movies")
                .retrieve().bodyToFlux(Movie.class)
                .flatMap(movie -> getReviews(movie.getId())
                        .map(review -> {
                            movie.addReview(review);
                            return movie;
                        })
                        .reduce((movie1, movie2) -> movie2)
                );
    }

    @GetMapping("/{movieId}")
    public Mono<Movie> getMovie(@PathVariable("movieId") Long movieId)
    {
        Mono<Movie> movie = webClient.build().get()
                .uri("lb://movie-service/api/movies/{movieId}", movieId)
                .retrieve().bodyToMono(Movie.class);

        Flux<MovieReview> reviews = getReviews(movieId);

        return Mono.zip(movie, reviews.collectList(), Movie::new);
    }

    private Flux<MovieReview> getReviews(Long movieId)
    {
        return webClient.build().get()
                .uri("lb://review-service/api/reviews?movieId={movieId}", movieId)
                .retrieve().bodyToFlux(MovieReview.class);
    }
}
