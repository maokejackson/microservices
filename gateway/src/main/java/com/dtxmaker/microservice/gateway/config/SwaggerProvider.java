package com.dtxmaker.microservice.gateway.config;

import com.dtxmaker.microservice.common.swagger.SwaggerConfigurator;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider
{
    private final RouteLocator      routeLocator;
    private final GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get()
    {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("api-gateway", SwaggerConfigurator.API_URL));

        List<String> routes = new ArrayList<>();

        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));

        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> {
                            String path = predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0");
                            String location = path.replace("/**", SwaggerConfigurator.API_URL);
                            resources.add(swaggerResource(routeDefinition.getId(), location));
                        })
                );

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location)
    {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }
}
