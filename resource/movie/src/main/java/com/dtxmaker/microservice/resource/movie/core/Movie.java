package com.dtxmaker.microservice.resource.movie.core;

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
public class Movie
{
    @Id
    private Long   id;
    private String title;
    private String poster;
}
