package com.dtxmaker.microservice.resource.review;

import com.dtxmaker.microservice.resource.review.core.MovieReview;
import com.dtxmaker.microservice.resource.review.core.MovieReviewRepository;

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
public class ReviewDbInitializer
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
    ApplicationRunner initReviews(MovieReviewRepository reviewRepository)
    {
        return args -> {
            MovieReview lordOfTheRings1 = new MovieReview();
            lordOfTheRings1.setAuthorName("James Christopher");
            lordOfTheRings1.setMovieId(1L);
            lordOfTheRings1.setReview("The sense of closure is exquisite, but I'm not sure I'm able to forgive Peter " +
                    "Jackson for the three years that he has taken to put it on the screen. The wait has been excruciating.");
            reviewRepository.save(lordOfTheRings1).block();

            MovieReview lordOfTheRings2 = new MovieReview();
            lordOfTheRings2.setAuthorName("Keith Phipps");
            lordOfTheRings2.setMovieId(1L);
            lordOfTheRings2
                    .setReview("The Return Of The King ultimately proves up to the series' increasingly difficult" +
                            " task: making movies that echo legends, making legends that reflect life, and reconciling it all "
                            +
                            "with the fact that both legends and lives all eventually meet their ends.");
            reviewRepository.save(lordOfTheRings2).block();

            MovieReview theLastSamurai1 = new MovieReview();
            theLastSamurai1.setAuthorName("Richard Schickel");
            theLastSamurai1.setMovieId(2L);
            theLastSamurai1
                    .setReview("It's easy to stand back and wax ironic about The Last Samurai. But it's not all " +
                            "that difficult to succumb to its full-spirited romanticism either.");
            reviewRepository.save(theLastSamurai1).block();

            MovieReview theLastSamurai2 = new MovieReview();
            theLastSamurai2.setAuthorName("Stuart Klawans");
            theLastSamurai2.setMovieId(2L);
            theLastSamurai2
                    .setReview("Mostly, though, The Last Samurai aims for, and achieves, epic sweep: the glory of" +
                            " tradition-bound warriors hurling themselves against the modern world, the grandeur of Hollywood "
                            +
                            "offering two points of view on everything.");
            reviewRepository.save(theLastSamurai2).block();

            reviewRepository.findAll().doOnEach(System.out::println).blockLast();
        };
    }
}
