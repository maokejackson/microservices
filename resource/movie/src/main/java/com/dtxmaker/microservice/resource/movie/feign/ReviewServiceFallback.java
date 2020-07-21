package com.dtxmaker.microservice.resource.movie.feign;

import com.dtxmaker.microservice.resource.movie.MovieReview;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Fallback class used for feign client, in case the hystrix circuit breaks
 */
@Component
public class ReviewServiceFallback implements ReviewsFeignClient
{
    @Override
    public CollectionModel<MovieReview> getMovieReviews(Long movieId)
    {
        return new CollectionModel<>(Collections.emptyList());
    }
}
