package com.dtxmaker.microservice.resource.review.core;

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

import java.util.Arrays;
import java.util.List;

@WebFluxTest(MovieReviewController.class)
class MovieReviewControllerTest
{
    @Autowired
    private WebTestClient client;

    @MockBean
    private MovieReviewRepository movieReviewRepository;

    @Test
    void givenMovieId_whenGetReviews_thenReturnListOfReviews() throws Exception
    {
        List<MovieReview> reviews = Arrays.asList(
                MovieReview.builder().id(1L).movieId(1L).authorName("author 1").review("review 1").build(),
                MovieReview.builder().id(2L).movieId(1L).authorName("author 2").review("review 2").build());

        given(movieReviewRepository.findAllByMovieId(1L)).willReturn(Flux.fromIterable(reviews));

        client.mutateWith(mockJwt())
                .get().uri("/api/reviews?movieId=1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(MovieReview.class).contains(reviews.toArray(new MovieReview[0])).hasSize(2);

        verify(movieReviewRepository, times(1)).findAllByMovieId(1L);
        verifyNoMoreInteractions(movieReviewRepository);
    }
}