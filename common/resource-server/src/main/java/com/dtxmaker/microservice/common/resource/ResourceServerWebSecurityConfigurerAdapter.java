package com.dtxmaker.microservice.common.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;

public abstract class ResourceServerWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter
{
    @Value("${keycloak.use-resource-role-mappings:false}")
    private boolean useResourceRoleMappings;

    @Value("${keycloak.principal-attribute:}")
    private String principalAttribute;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // @formatter:off
        http.cors()
                .and()
            .csrf()
                .disable()
            .httpBasic()
                .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
        ;
        // @formatter:on
    }

    protected abstract String oauth2ClientId();

    private JwtAuthenticationConverter jwtAuthenticationConverter()
    {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return converter;
    }

    private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter()
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
