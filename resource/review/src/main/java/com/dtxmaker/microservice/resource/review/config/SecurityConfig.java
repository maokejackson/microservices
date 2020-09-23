package com.dtxmaker.microservice.resource.review.config;

import com.dtxmaker.microservice.common.reactive.resource.ResourceServerConfiguration;
import com.dtxmaker.microservice.common.reactive.resource.ResourceServerWebSecurityConfigurerAdapter;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

@ResourceServerConfiguration
@EnableReactiveMethodSecurity
public class SecurityConfig extends ResourceServerWebSecurityConfigurerAdapter
{
    @Override
    protected void configure(ServerHttpSecurity http)
    {
        super.configure(http);
        // @formatter:off
        http.authorizeExchange()
                .matchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyExchange().authenticated()
        ;
        // @formatter:on
    }
}
