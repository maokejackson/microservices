package com.dtxmaker.microservice.gui.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

public class OAuth2AuthorizedClientInterceptor implements ClientHttpRequestInterceptor
{
    private final OAuth2AuthorizedClientManager clientManager;

    public OAuth2AuthorizedClientInterceptor(OAuth2AuthorizedClientManager clientManager)
    {
        this.clientManager = clientManager;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException
    {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak")
                .principal(principal)
                .build();
        OAuth2AuthorizedClient authorizedClient = clientManager.authorize(authorizeRequest);

        if (authorizedClient != null)
        {
            HttpHeaders headers = request.getHeaders();
            headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
        }

        return execution.execute(request, body);
    }
}
