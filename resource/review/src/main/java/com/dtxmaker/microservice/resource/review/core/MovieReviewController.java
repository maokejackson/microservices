package com.dtxmaker.microservice.resource.review.core;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Api(tags = "Review")
@RequestMapping("/api/reviews")
@RestController
public class MovieReviewController
{
    @Autowired
    private MovieReviewRepository repository;

    @ApiOperation(value = "Get all movie reviews", response = MovieReview.class)
    @GetMapping(params = "!movieId")
    public Flux<MovieReview> getAllReviews()
    {
        return repository.findAll();
    }

    @ApiOperation(value = "Get all reviews of specific movie", response = MovieReview.class)
    @GetMapping
    public Flux<MovieReview> getReviews(@RequestParam("movieId") Long movieId)
    {
        return repository.findAllByMovieId(movieId);
    }
}
