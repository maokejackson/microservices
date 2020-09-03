package com.dtxmaker.microservice.resource.review.core;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(MovieReviewController.class)
class MovieReviewControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieReviewRepository movieReviewRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenMovieId_whenGetReviews_thenReturnListOfReviews() throws Exception
    {
        List<MovieReview> reviews = Arrays.asList(
                MovieReview.builder().id(1L).movieId(1L).authorName("author 1").review("review 1").build(),
                MovieReview.builder().id(2L).movieId(1L).authorName("author 2").review("review 2").build());

        given(movieReviewRepository.findAllByMovieId(1L)).willReturn(reviews);

        mockMvc.perform(get("/api/reviews?movieId=1").with(jwt()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(reviews)))
                .andExpect(status().isOk());
    }
}