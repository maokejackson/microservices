package com.dtxmaker.microservice.common.reactive.resource;

import com.dtxmaker.microservice.common.swagger.SwaggerConfigurator;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

public abstract class ResourceServerWebSecurityConfigurerAdapter
{
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
    {
        configure(http);
        return http.build();
    }

    protected void configure(ServerHttpSecurity http)
    {
        // @formatter:off
        http.csrf()
                .disable()
            .httpBasic()
                .disable()
            .authorizeExchange()
                .pathMatchers(SwaggerConfigurator.RESOURCES).permitAll()
                .and()
            .oauth2ResourceServer()
                .jwt()
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
        ;
        // @formatter:on
    }

    private ReactiveJwtAuthenticationConverter jwtAuthenticationConverter()
    {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter());
        return converter;
    }
}
