package com.dtxmaker.microservice.auth;

import com.dtxmaker.microservice.auth.config.KeycloakServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@EnableEurekaClient
@SpringBootApplication
@EnableConfigurationProperties(KeycloakServerProperties.class)
public class AuthorizationServiceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
    }
}
