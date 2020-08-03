package com.dtxmaker.microservice.resource.movie.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Fallback class used for feign client, in case the hystrix circuit breaks
 * This allows access to the underlying exception that broke the circuit
 */
@Slf4j
@Component
public class ReviewServiceFallbackFactory implements FallbackFactory<ReviewsFeignClient>
{
    @Override
    public ReviewsFeignClient create(Throwable throwable)
    {
        return (authHeader, movieId) -> {
            log.error("Error occurred trying to fetch reviews from review service", throwable);
            return Collections.emptyList();
        };
    }
}
