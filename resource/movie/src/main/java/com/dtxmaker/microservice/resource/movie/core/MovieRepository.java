package com.dtxmaker.microservice.resource.movie.core;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MovieRepository extends R2dbcRepository<Movie, Long>
{
}
