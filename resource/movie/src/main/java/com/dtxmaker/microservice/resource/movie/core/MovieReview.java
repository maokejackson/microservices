package com.dtxmaker.microservice.resource.movie.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieReview
{
    private Long   id;
    private Long   movieId;
    private String review;
    private String authorName;
}
