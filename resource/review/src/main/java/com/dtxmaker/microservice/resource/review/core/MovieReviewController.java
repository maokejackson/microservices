package com.dtxmaker.microservice.resource.review.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/reviews")
@RestController
public class MovieReviewController
{
    @Autowired
    private MovieReviewRepository repository;

    @GetMapping
    public List<MovieReview> getReviews(@RequestParam("movieId") Long movieId)
    {
        return repository.findAllByMovieId(movieId);
    }
}
