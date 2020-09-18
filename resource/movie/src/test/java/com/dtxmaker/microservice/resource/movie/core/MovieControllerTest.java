package com.dtxmaker.microservice.resource.movie.core;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@WebFluxTest(MovieController.class)
class MovieControllerTest
{
    @Autowired
    private WebTestClient client;

    @MockBean
    private MovieRepository movieRepository;

    @Test
    void givenNoArgument_whenGetMovies_thenReturnListOfMovies() throws Exception
    {
        List<Movie> movies = Arrays.asList(
                Movie.builder().id(1L).title("Movie 1").poster("Poster 1").build(),
                Movie.builder().id(2L).title("Movie 2").poster("Poster 2").build());

        given(movieRepository.findAll()).willReturn(Flux.fromIterable(movies));

        client.mutateWith(mockJwt())
                .get().uri("/api/movies")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Movie.class).hasSize(2).contains(movies.toArray(new Movie[0]));

        verify(movieRepository, times(1)).findAll();
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    void givenExistingMovieId_whenGetMovie_thenReturnMovieObject() throws Exception
    {
        Movie movie = Movie.builder().id(1L).title("Movie 1").poster("Poster 1").build();

        given(movieRepository.findById(1L)).willReturn(Mono.just(movie));

        client.mutateWith(mockJwt())
                .get().uri("/api/movies/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Movie.class).isEqualTo(movie);

        verify(movieRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    void givenNonExistingMovieId_whenGetMovie_thenReturnEmptyObject() throws Exception
    {
        given(movieRepository.findById(1L)).willReturn(Mono.empty());

        client.mutateWith(mockJwt())
                .get().uri("/api/movies/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Movie.class).hasSize(0);

        verify(movieRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(movieRepository);
    }
}