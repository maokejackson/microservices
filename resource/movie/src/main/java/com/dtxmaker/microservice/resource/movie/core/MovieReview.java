package com.dtxmaker.microservice.resource.movie.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieReview
{
    private Long   id;
    private Long   movieId;
    private String review;
    private String authorName;
}
