package com.dtxmaker.microservice.gui.movie;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Movie
{
    private Long              id;
    private String            title;
    private String            poster;
    private List<MovieReview> reviews;
}
