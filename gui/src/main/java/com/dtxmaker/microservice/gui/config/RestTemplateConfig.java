package com.dtxmaker.microservice.gui.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

@Configuration
public class RestTemplateConfig
{
    private final DiscoveryClient discoveryClient;

    public RestTemplateConfig(DiscoveryClient discoveryClient)
    {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository)
    {
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    public RestTemplate restTemplate(OAuth2AuthorizedClientManager clientManager)
    {
        List<ServiceInstance> instances = discoveryClient.getInstances("api-gateway");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new OAuth2AuthorizedClientInterceptor(clientManager));

        if (!instances.isEmpty())
        {
            ServiceInstance instance = instances.get(0);
            String baseUri = instance.getUri().toString();
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUri));
        }

        return restTemplate;
    }

    @Bean
    public RestTemplateHelper restTemplateHelper(RestTemplate restTemplate)
    {
        return new RestTemplateHelper(restTemplate);
    }
}
