package com.dtxmaker.microservice.gui.config;

import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfig
{
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate(KeycloakClientRequestFactory keycloakClientRequestFactory)
    {
        KeycloakRestTemplate restTemplate = new KeycloakRestTemplate(keycloakClientRequestFactory);
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8084"));
        return restTemplate;
    }

    @Bean
    public RestTemplateHelper restTemplateHelper(KeycloakRestTemplate keycloakRestTemplate)
    {
        return new RestTemplateHelper(keycloakRestTemplate);
    }
}
