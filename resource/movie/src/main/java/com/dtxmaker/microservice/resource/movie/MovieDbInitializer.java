package com.dtxmaker.microservice.resource.movie;

import com.dtxmaker.microservice.resource.movie.core.Movie;
import com.dtxmaker.microservice.resource.movie.core.MovieRepository;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

@Service
public class MovieDbInitializer
{
    @Autowired
    void initializeDatabase(ConnectionFactory connectionFactory)
    {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource schema = resourceLoader.getResource("classpath:schema.sql");
        Resource data = resourceLoader.getResource("classpath:data.sql");

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        if (schema.exists()) populator.addScript(schema);
        if (data.exists()) populator.addScript(data);

        populator.execute(connectionFactory).block();
    }

    @Bean
    ApplicationRunner initReviews(MovieRepository movieRepository)
    {
        return args -> {
            Movie lordOfTheRings = new Movie();
            lordOfTheRings.setTitle("The Lord Of The Rings: The Return of the King");
            lordOfTheRings.setPoster(
                    "https://resizing.flixster.com/0HK1Y-onFu90kMEV1KfRbs7-WGE=/206x305/v1.bTsxMTE2NjQyMztqOzE4NDQ0OzEyMDA7ODAwOzEyMDA");
            movieRepository.save(lordOfTheRings).block();

            Movie theLastSamurai = new Movie();
            theLastSamurai.setTitle("The Last Samurai");
            theLastSamurai.setPoster(
                    "https://resizing.flixster.com/bJPMRIGxIceRp965aQ6Htekf-xM=/206x305/v1.bTsxMTE2Njg2MTtqOzE4NDQ0OzEyMDA7ODAwOzEyMDA");
            movieRepository.save(theLastSamurai).block();

            movieRepository.findAll().doOnEach(System.out::println).blockLast();
        };
    }
}
