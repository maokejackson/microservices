package com.dtxmaker.microservice.resource.review.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class MovieReview
{
    @Id
    private Long   id;
    private Long   movieId;
    private String review;
    private String authorName;
}
