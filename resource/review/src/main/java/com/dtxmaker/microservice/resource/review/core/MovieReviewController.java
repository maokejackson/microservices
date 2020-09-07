package com.dtxmaker.microservice.resource.review.core;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Review")
@RequestMapping("/api/reviews")
@RestController
public class MovieReviewController
{
    @Autowired
    private MovieReviewRepository repository;

    @ApiOperation(value = "Get list of movie reviews", response = MovieReview.class)
    @GetMapping
    public List<MovieReview> getReviews(@RequestParam("movieId") Long movieId)
    {
        return repository.findAllByMovieId(movieId);
    }
}
