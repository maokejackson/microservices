package com.dtxmaker.microservice.resource.review.core;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovieReview
{
    @Id
    @GeneratedValue
    private Long   id;
    private Long   movieId;
    private String review;
    private String authorName;
}
