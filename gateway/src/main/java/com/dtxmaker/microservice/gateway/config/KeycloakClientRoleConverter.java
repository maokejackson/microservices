package com.dtxmaker.microservice.gateway.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class KeycloakClientRoleConverter implements Converter<Jwt, Flux<GrantedAuthority>>
{
    private static final String RESOURCE_ACCESS = "resource_access";
    private static final String ROLES           = "roles";
    private static final String ROLE_PREFIX     = "ROLE_";

    private final String clientId;

    public KeycloakClientRoleConverter(String clientId)
    {
        this.clientId = Objects.requireNonNull(clientId);
    }

    @Override
    public Flux<GrantedAuthority> convert(final Jwt jwt)
    {
        Map<String, Map<String, List<String>>> authorities = jwt.getClaim(RESOURCE_ACCESS);

        if (authorities == null)
        {
            return Flux.empty();
        }

        List<GrantedAuthority> list = authorities.getOrDefault(clientId, Collections.emptyMap())
                .getOrDefault(ROLES, Collections.emptyList()).stream()
                .map(roleName -> ROLE_PREFIX + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return Mono.just(list).flatMapMany(Flux::fromIterable);
    }
}