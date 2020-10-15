package com.dtxmaker.microservice.gui.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import javax.net.ssl.SSLContext;

@RequiredArgsConstructor
@Configuration
public class RestTemplateConfig
{
    private final DiscoveryClient discoveryClient;

    @Bean
    public DefaultOAuth2AuthorizedClientManager defaultAuthorizedClientManager(
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
    public ClientHttpRequestFactory clientHttpRequestFactory(ServerProperties serverProperties) throws Exception
    {
        URL trustStore = new URL(serverProperties.getSsl().getTrustStore());
        char[] trustStorePassword = serverProperties.getSsl().getTrustStorePassword().toCharArray();

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore, trustStorePassword)
                .build();

        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext))
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public DefaultRestTemplate defaultRestTemplate(DefaultOAuth2AuthorizedClientManager clientManager,
            ClientHttpRequestFactory requestFactory)
    {
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getInterceptors().add(new OAuth2AuthorizedClientInterceptor(clientManager, "cinema-app"));
        return new DefaultRestTemplate(restTemplate, discoveryClient, "api-gateway");
    }
}
