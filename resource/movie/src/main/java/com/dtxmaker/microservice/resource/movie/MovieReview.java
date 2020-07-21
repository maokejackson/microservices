package com.dtxmaker.microservice.resource.movie;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
public class MovieReview extends RepresentationModel<MovieReview>
{
    private Long   id;
    private Long   movieId;
    private String review;
    private String authorName;
}
