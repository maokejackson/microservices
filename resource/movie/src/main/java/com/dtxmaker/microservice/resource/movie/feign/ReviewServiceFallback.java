package com.dtxmaker.microservice.resource.movie.feign;

import com.dtxmaker.microservice.resource.movie.core.MovieReview;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Fallback class used for feign client, in case the hystrix circuit breaks
 */
@Component
public class ReviewServiceFallback implements ReviewsFeignClient
{
    @Override
    public List<MovieReview> getMovieReviews(String authHeader, Long movieId)
    {
        return Collections.emptyList();
    }
}
