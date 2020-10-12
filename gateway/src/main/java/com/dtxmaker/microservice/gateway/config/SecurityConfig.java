package com.dtxmaker.microservice.gateway.config;

import com.dtxmaker.microservice.common.reactive.resource.ResourceServerConfiguration;
import com.dtxmaker.microservice.common.reactive.resource.ResourceServerWebSecurityConfigurerAdapter;
import com.dtxmaker.microservice.common.swagger.SwaggerConfigurator;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@ResourceServerConfiguration
@EnableReactiveMethodSecurity
public class SecurityConfig extends ResourceServerWebSecurityConfigurerAdapter
{
    private final RouteLocator      routeLocator;
    private final GatewayProperties gatewayProperties;

    @Override
    protected void configure(ServerHttpSecurity http)
    {
        super.configure(http);
        configureSwagger(http);

        // @formatter:off
        http.authorizeExchange()
                .matchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyExchange().authenticated()
                .and()
            .oauth2Client()
        ;
        // @formatter:on
    }

    private void configureSwagger(ServerHttpSecurity http)
    {
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));

        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> {
                            String path = predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0");
                            String location = path.replace("/**", "");
                            Arrays.stream(SwaggerConfigurator.RESOURCES)
                                    .map(resource -> location + resource)
                                    .forEach(resource -> http.authorizeExchange().pathMatchers(resource).permitAll());
                        })
                );
    }
}
