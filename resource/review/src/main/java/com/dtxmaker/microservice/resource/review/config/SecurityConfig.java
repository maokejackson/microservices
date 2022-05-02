package com.dtxmaker.microservice.resource.review.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableReactiveMethodSecurity
public class SecurityConfig
{
    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
    {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(customizer -> customizer
                        .matchers(EndpointRequest.toAnyEndpoint()).permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(customizer -> customizer.jwt(withDefaults()))
                .build();
    }
}
