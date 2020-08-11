package com.dtxmaker.microservice.gui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    public void configure(WebSecurity web)
    {
        // @formatter:off
        web
            .ignoring()
                .antMatchers("/webjars/**");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
            .oauth2Login()
        ;
        // @formatter:on
    }
}
