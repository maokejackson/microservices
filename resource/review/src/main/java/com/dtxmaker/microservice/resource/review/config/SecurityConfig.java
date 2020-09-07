package com.dtxmaker.microservice.resource.review.config;

import com.dtxmaker.microservice.common.resource.ResourceServerConfiguration;
import com.dtxmaker.microservice.common.resource.ResourceServerWebSecurityConfigurerAdapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@ResourceServerConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends ResourceServerWebSecurityConfigurerAdapter
{
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/api",
            "/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };

    @Value("${spring.security.oauth2.client.registration.review-service.client-id}")
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
                .antMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
        ;
        // @formatter:on
    }
}
