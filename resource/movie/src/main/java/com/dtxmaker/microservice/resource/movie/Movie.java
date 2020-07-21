package com.dtxmaker.microservice.resource.movie;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Movie
{
    @Id
    @GeneratedValue
    private Long   id;
    private String title;
    private String poster;
}
