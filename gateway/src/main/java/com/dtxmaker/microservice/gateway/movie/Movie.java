package com.dtxmaker.microservice.gateway.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie
{
    private Long              id;
    private String            title;
    private String            poster;
    private List<MovieReview> reviews;

    public Movie(Movie movie, List<MovieReview> reviews)
    {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.poster = movie.getPoster();
        this.reviews = reviews;
    }

    public void addReview(MovieReview review)
    {
        if (reviews == null)
        {
            reviews = new ArrayList<>();
        }
        reviews.add(review);
    }
}
