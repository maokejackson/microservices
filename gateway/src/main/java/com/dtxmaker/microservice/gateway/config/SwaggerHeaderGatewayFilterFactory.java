package com.dtxmaker.microservice.gateway.config;

import com.dtxmaker.microservice.common.swagger.SwaggerConfigurator;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SwaggerHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<Object>
{
    private static final String HEADER_NAME = "X-Forwarded-Prefix";

    @Override
    public GatewayFilter apply(Object config)
    {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            if (!StringUtils.endsWithIgnoreCase(path, SwaggerConfigurator.API_URL))
            {
                return chain.filter(exchange);
            }

            String basePath = path.substring(0, path.lastIndexOf(SwaggerConfigurator.API_URL));
            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        };
    }
}
