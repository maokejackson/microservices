package com.dtxmaker.microservice.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig
{
    @Value("${spring.security.oauth2.client.registration.api-gateway.client-id}")
    private String clientId;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
    {
        // @formatter:off
        http.csrf()
                .disable()
            .authorizeExchange()
                .matchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyExchange().authenticated()
                .and()
            .oauth2Client()
                .and()
            .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
        ;
        // @formatter:on
        return http.build();
    }

    private ReactiveJwtAuthenticationConverter jwtAuthenticationConverter()
    {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakClientRoleConverter(clientId));
        return converter;
    }

    @Bean
    public JwtDecoder jwtDecoderByIssuerUri(OAuth2ResourceServerProperties properties)
    {
        final String jwkSetUri = properties.getJwt().getJwkSetUri();
        final NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
        // Use preferred_username from claims as authentication name, instead of UUID subject
        jwtDecoder.setClaimSetConverter(new UsernameSubClaimAdapter());
        return jwtDecoder;
    }
}
