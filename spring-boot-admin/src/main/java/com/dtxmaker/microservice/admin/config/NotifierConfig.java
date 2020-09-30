package com.dtxmaker.microservice.admin.config;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.LoggingNotifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotifierConfig
{
    private final InstanceRepository repository;

    public NotifierConfig(InstanceRepository repository)
    {
        this.repository = repository;
    }

    @Bean
    public LoggingNotifier loggingNotifier()
    {
        return new LoggingNotifier(repository);
    }
}
