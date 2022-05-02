package com.dtxmaker.microservice.resource.review.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("/api/reviews")
@RestController
public class MovieReviewController
{
    @Autowired
    private MovieReviewRepository repository;

    @GetMapping(params = "!movieId")
    public Flux<MovieReview> getAllReviews()
    {
        return repository.findAll();
    }

    @GetMapping
    public Flux<MovieReview> getReviews(@RequestParam("movieId") Long movieId)
    {
        return repository.findAllByMovieId(movieId);
    }
}
