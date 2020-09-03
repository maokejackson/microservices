package com.dtxmaker.microservice.resource.movie.core;

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
public class Movie
{
    @Id
    @GeneratedValue
    private Long   id;
    private String title;
    private String poster;
}
