package com.dtxmaker.microservice.gui.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
public class DefaultRestTemplate
{
    private final RestTemplate    restTemplate;
    private final DiscoveryClient discoveryClient;
    private final String          gatewayApplicationName;

    private void discoverGateway()
    {
        List<ServiceInstance> instances = discoveryClient.getInstances(gatewayApplicationName);

        UriTemplateHandler uriTemplateHandler = instances.stream()
                .findAny()
                .map(ServiceInstance::getUri)
                .map(URI::toString)
                .map(DefaultUriBuilderFactory::new)
                .orElse(new DefaultUriBuilderFactory());

        restTemplate.setUriTemplateHandler(uriTemplateHandler);
    }

    public <T> T get(String url, Class<T> responseType, Object... uriVariables)
    {
        discoverGateway();
        return restTemplate.getForObject(url, responseType, uriVariables);
    }

    public <T> T get(String url, Object request, Class<T> responseType, Object... uriVariables)
    {
        discoverGateway();
        HttpEntity<Object> requestEntity = new HttpEntity<>(request);
        return restTemplate.exchange(url, GET, requestEntity, responseType, uriVariables).getBody();
    }

    public <T> T get(String url, ParameterizedTypeReference<T> responseType, Object... uriVariables)
    {
        discoverGateway();
        return restTemplate.exchange(url, GET, null, responseType, uriVariables).getBody();
    }

    public <T> T post(String url, Object request, Class<T> responseType, Object... uriVariables)
    {
        discoverGateway();
        return restTemplate.postForObject(url, request, responseType, uriVariables);
    }

    public <T> T put(String url, Object request, Class<T> responseType, Object... uriVariables)
    {
        discoverGateway();
        HttpEntity<Object> requestEntity = new HttpEntity<>(request);
        return restTemplate.exchange(url, PUT, requestEntity, responseType, uriVariables).getBody();
    }

    public <T> T patch(String url, Object request, Class<T> responseType, Object... uriVariables)
    {
        discoverGateway();
        return restTemplate.patchForObject(url, request, responseType, uriVariables);
    }

    public void delete(String url, Object... uriVariables)
    {
        discoverGateway();
        restTemplate.delete(url, uriVariables);
    }
}
