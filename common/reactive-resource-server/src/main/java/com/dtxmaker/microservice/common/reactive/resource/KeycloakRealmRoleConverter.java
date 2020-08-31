package com.dtxmaker.microservice.common.reactive.resource;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Flux<GrantedAuthority>>
{
    public static final String REALM_ACCESS = "realm_access";
    public static final String ROLES        = "roles";
    public static final String ROLE_PREFIX  = "ROLE_";

    @Override
    public Flux<GrantedAuthority> convert(final Jwt jwt)
    {
        final Map<String, List<String>> authorities = jwt.getClaim(REALM_ACCESS);

        if (authorities == null) return Flux.empty();

        List<GrantedAuthority> list = authorities.get(ROLES).stream()
                .map(roleName -> ROLE_PREFIX + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return Mono.just(list).flatMapMany(Flux::fromIterable);
    }
}