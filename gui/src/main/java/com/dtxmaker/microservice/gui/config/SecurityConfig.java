package com.dtxmaker.microservice.gui.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository)
    {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public void configure(WebSecurity web)
    {
        // @formatter:off
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        ;
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // @formatter:off
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .and()
            .oauth2Login()
                .and()
            .logout()
                .logoutSuccessHandler(oidcLogoutSuccessHandler())
        ;
        // @formatter:on
    }

    private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler()
    {
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

        return oidcLogoutSuccessHandler;
    }
}
