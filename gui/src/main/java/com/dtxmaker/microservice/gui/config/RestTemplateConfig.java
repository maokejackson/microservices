package com.dtxmaker.microservice.gui.config;

import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

@Configuration
public class RestTemplateConfig
{
    @Autowired
    private DiscoveryClient discoveryClient;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate(KeycloakClientRequestFactory keycloakClientRequestFactory)
    {
        List<ServiceInstance> instances = discoveryClient.getInstances("api-gateway");

        if (instances.isEmpty())
        {
            return new KeycloakRestTemplate(keycloakClientRequestFactory);
        }
        else
        {
            ServiceInstance instance = instances.get(0);
            String baseUri = instance.getUri().toString();

            KeycloakRestTemplate restTemplate = new KeycloakRestTemplate(keycloakClientRequestFactory);
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUri));
            return restTemplate;
        }
    }

    @Bean
    public RestTemplateHelper restTemplateHelper(KeycloakRestTemplate keycloakRestTemplate)
    {
        return new RestTemplateHelper(keycloakRestTemplate);
    }
}
