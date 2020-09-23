package com.dtxmaker.microservice.common.resource;

import com.dtxmaker.microservice.common.swagger.SwaggerConfigurator;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

public abstract class ResourceServerWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter
{
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
        ;
        // @formatter:on
    }
}
