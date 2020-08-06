package com.dtxmaker.microservice.gui.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

public class RestTemplateHelper
{
    private final RestTemplate restTemplate;

    public RestTemplateHelper()
    {
        this(null);
    }

    public RestTemplateHelper(String accessToken)
    {
        this.restTemplate = new RestTemplate();

        if (accessToken != null)
        {
            this.restTemplate.getInterceptors().add(getBearerTokenInterceptor(accessToken));
        }
        else
        {
            this.restTemplate.getInterceptors().add(getNoTokenInterceptor());
        }
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken)
    {
        return (request, bytes, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, bytes);
        };
    }

    private ClientHttpRequestInterceptor getNoTokenInterceptor()
    {
        return (request, bytes, execution) -> {
            throw new IllegalStateException("Can't access the API without an access token");
        };
    }

    public <T> T get(String url, Class<T> responseType, Object... uriVariables)
    {
        return restTemplate.getForObject(url, responseType, uriVariables);
    }

    public <T> T get(String url, Object request, Class<T> responseType, Object... uriVariables)
    {
        HttpEntity<Object> requestEntity = new HttpEntity<>(request);
        return restTemplate.exchange(url, GET, requestEntity, responseType, uriVariables).getBody();
    }

    public <T> T get(String url, ParameterizedTypeReference<T> responseType, Object... uriVariables)
    {
        return restTemplate.exchange(url, GET, null, responseType, uriVariables).getBody();
    }

    public <T> T post(String url, Object request, Class<T> responseType, Object... uriVariables)
    {
        return restTemplate.postForObject(url, request, responseType, uriVariables);
    }

    public <T> T put(String url, Object request, Class<T> responseType, Object... uriVariables)
    {
        HttpEntity<Object> requestEntity = new HttpEntity<>(request);
        return restTemplate.exchange(url, PUT, requestEntity, responseType, uriVariables).getBody();
    }

    public <T> T patch(String url, Object request, Class<T> responseType, Object... uriVariables)
    {
        return restTemplate.patchForObject(url, request, responseType, uriVariables);
    }

    public void delete(String url, Object... uriVariables)
    {
        restTemplate.delete(url, uriVariables);
    }
}
