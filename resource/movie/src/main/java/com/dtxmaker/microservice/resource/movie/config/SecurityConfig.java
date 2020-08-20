package com.dtxmaker.microservice.resource.movie.config;

import com.dtxmaker.microservice.common.resource.ResourceServerConfiguration;
import com.dtxmaker.microservice.common.resource.ResourceServerWebSecurityConfigurerAdapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@ResourceServerConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends ResourceServerWebSecurityConfigurerAdapter
{
    @Value("${spring.security.oauth2.client.registration.movie-service.client-id}")
    private String clientId;

    @Override
    protected String oauth2ClientId()
    {
        return clientId;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        super.configure(http);
        // @formatter:off
        http.authorizeRequests()
                .anyRequest().authenticated()
        ;
        // @formatter:on
    }
}
