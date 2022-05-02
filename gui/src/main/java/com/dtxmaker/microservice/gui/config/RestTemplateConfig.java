package com.dtxmaker.microservice.gui.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig
{
    @LoadBalanced
    @Bean
    public RestTemplate loadBalancedRestTemplate(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository)
    {
        DefaultOAuth2AuthorizedClientManager clientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new OAuth2AuthorizedClientInterceptor(clientManager, "cinema-app"));
        return restTemplate;
    }
}
