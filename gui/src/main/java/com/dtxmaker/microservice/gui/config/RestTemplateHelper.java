package com.dtxmaker.microservice.gui.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateHelper
{
    private final RestTemplate restTemplate;

    public RestTemplateHelper(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
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
