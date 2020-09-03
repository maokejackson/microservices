package com.dtxmaker.microservice.resource.movie.core;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dtxmaker.microservice.resource.movie.feign.ReviewsFeignClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(MovieController.class)
class MovieControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private ReviewsFeignClient reviewsFeignClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenNoArgument_whenGetMovies_thenReturnListOfMovies() throws Exception
    {
        List<Movie> movies = Arrays.asList(
                Movie.builder().id(1L).title("Movie 1").poster("Poster 1").build(),
                Movie.builder().id(2L).title("Movie 2").poster("Poster 2").build());

        given(movieRepository.findAll()).willReturn(movies);

        List<MovieReview> reviews1 = Arrays.asList(
                MovieReview.builder().id(1L).movieId(1L).authorName("author 1").review("review 1").build(),
                MovieReview.builder().id(2L).movieId(1L).authorName("author 2").review("review 2").build());

        given(reviewsFeignClient.getMovieReviews(1L)).willReturn(reviews1);

        List<MovieReview> reviews2 = Arrays.asList(
                MovieReview.builder().id(3L).movieId(2L).authorName("author 3").review("review 3").build(),
                MovieReview.builder().id(4L).movieId(2L).authorName("author 4").review("review 4").build());

        given(reviewsFeignClient.getMovieReviews(2L)).willReturn(reviews2);

        List<MovieDTO> expected = Arrays.asList(
                new MovieDTO(movies.get(0), reviews1),
                new MovieDTO(movies.get(1), reviews2)
        );

        mockMvc.perform(get("/api/movies").with(jwt()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isOk());
    }

    @Test
    void givenExistingMovieId_whenGetMovie_thenReturnMovieObject() throws Exception
    {
        Movie movie = Movie.builder().id(1L).title("Movie 1").poster("Poster 1").build();

        given(movieRepository.findById(1L)).willReturn(Optional.of(movie));

        List<MovieReview> reviews = Arrays.asList(
                MovieReview.builder().id(1L).movieId(1L).authorName("author 1").review("review 1").build(),
                MovieReview.builder().id(2L).movieId(1L).authorName("author 2").review("review 2").build());

        given(reviewsFeignClient.getMovieReviews(1L)).willReturn(reviews);

        mockMvc.perform(get("/api/movies/1").with(jwt()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new MovieDTO(movie, reviews))))
                .andExpect(status().isOk());
    }

    @Test
    void givenNonExistingMovieId_whenGetMovie_thenReturnNotFound() throws Exception
    {
        given(movieRepository.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/movies/1").with(jwt()))
                .andExpect(content().string("Movie not found"))
                .andExpect(status().isNotFound());
    }
}