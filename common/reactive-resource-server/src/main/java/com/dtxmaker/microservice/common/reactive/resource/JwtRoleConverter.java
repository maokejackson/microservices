package com.dtxmaker.microservice.common.reactive.resource;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class JwtRoleConverter implements Converter<Jwt, Flux<GrantedAuthority>>
{
    public static final String ROLES_CLAIM = "roles";
    public static final String ROLE_PREFIX = "ROLE_";

    @Override
    public Flux<GrantedAuthority> convert(final Jwt jwt)
    {
        if (!jwt.containsClaim(ROLES_CLAIM))
        {
            return Flux.empty();
        }

        final List<String> roles = jwt.getClaim(ROLES_CLAIM);

        List<GrantedAuthority> authorities = roles.stream()
                .map(roleName -> ROLE_PREFIX + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return Mono.just(authorities).flatMapMany(Flux::fromIterable);
    }
}