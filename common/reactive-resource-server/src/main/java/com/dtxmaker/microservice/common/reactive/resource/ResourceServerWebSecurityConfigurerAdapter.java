package com.dtxmaker.microservice.common.reactive.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

public abstract class ResourceServerWebSecurityConfigurerAdapter
{
    @Value("${keycloak.use-resource-role-mappings:false}")
    private boolean useResourceRoleMappings;

    @Value("${keycloak.principal-attribute:}")
    private String principalAttribute;

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
            .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
        ;
        // @formatter:on
    }

    protected abstract String oauth2ClientId();

    private ReactiveJwtAuthenticationConverter jwtAuthenticationConverter()
    {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return converter;
    }

    private Converter<Jwt, Flux<GrantedAuthority>> jwtGrantedAuthoritiesConverter()
    {
        if (useResourceRoleMappings)
        {
            return new KeycloakResourceRoleConverter(oauth2ClientId());
        }
        else
        {
            return new KeycloakRealmRoleConverter();
        }
    }

    @Bean
    public JwtDecoder jwtDecoderByIssuerUri(OAuth2ResourceServerProperties properties)
    {
        final String jwkSetUri = properties.getJwt().getJwkSetUri();
        final NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
        if (StringUtils.hasText(principalAttribute))
        {
            jwtDecoder.setClaimSetConverter(new UsernameSubClaimAdapter(principalAttribute));
        }
        return jwtDecoder;
    }
}
