package com.dtxmaker.microservice.gateway.config;

import com.dtxmaker.microservice.common.reactive.resource.ResourceServerConfiguration;
import com.dtxmaker.microservice.common.reactive.resource.ResourceServerWebSecurityConfigurerAdapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

@ResourceServerConfiguration
@EnableReactiveMethodSecurity
public class SecurityConfig extends ResourceServerWebSecurityConfigurerAdapter
{
    @Value("${spring.security.oauth2.client.registration.api-gateway.client-id}")
    private String clientId;

    @Override
    protected String oauth2ClientId()
    {
        return clientId;
    }

    @Override
    protected void configure(ServerHttpSecurity http)
    {
        super.configure(http);
        // @formatter:off
        http.authorizeExchange()
                .matchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyExchange().authenticated()
                .and()
            .oauth2Client()
        ;
        // @formatter:on
    }
}
