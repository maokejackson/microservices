package com.dtxmaker.microservice.resource.review.core;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface MovieReviewRepository extends R2dbcRepository<MovieReview, Long>
{
    Flux<MovieReview> findAllByMovieId(Long movieId);
}
