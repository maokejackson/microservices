package com.dtxmaker.microservice.discovery.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http.authorizeRequests(customizer -> customizer
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(customizer -> customizer.defaultSuccessUrl("/", true))
                .csrf(customizer -> customizer.ignoringAntMatchers("/eureka/**"))
                .build();
    }
}
