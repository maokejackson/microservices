package com.dtxmaker.microservice.resource.movie.feign;

import com.dtxmaker.microservice.resource.movie.MovieReview;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "review-service", /*fallback = ReviewServiceFallback.class,*/ fallbackFactory = ReviewServiceFallbackFactory.class)
public interface ReviewsFeignClient
{
    @GetMapping("/reviews/search/findAllByMovieId")
    CollectionModel<MovieReview> getMovieReviews(@RequestParam("movieID") Long movieId);
}