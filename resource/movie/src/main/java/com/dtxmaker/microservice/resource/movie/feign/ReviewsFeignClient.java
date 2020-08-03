package com.dtxmaker.microservice.resource.movie.feign;

import com.dtxmaker.microservice.resource.movie.MovieReview;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "review-service", /*fallback = ReviewServiceFallback.class,*/ fallbackFactory = ReviewServiceFallbackFactory.class)
public interface ReviewsFeignClient
{
    @GetMapping("/api/reviews")
    List<MovieReview> getMovieReviews(@RequestHeader("Authorization") String authHeader,
            @RequestParam("movieId") Long movieId);
}