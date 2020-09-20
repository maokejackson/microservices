package com.dtxmaker.microservice.common.resource;

import com.dtxmaker.microservice.common.swagger.SwaggerConfigurator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;

public abstract class ResourceServerWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter
{
    @Value("${keycloak.use-resource-role-mappings:false}")
    private boolean useResourceRoleMappings;

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
            .authorizeRequests()
                .antMatchers(SwaggerConfigurator.RESOURCES).permitAll()
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
}
