package com.dtxmaker.microservice.resource.review.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakClientRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>>
{
    private static final String RESOURCE_ACCESS = "resource_access";
    private static final String RESOURCE_ID     = "review-service";
    private static final String ROLES           = "roles";
    private static final String ROLE_PREFIX     = "ROLE_";

    @Override
    public Collection<GrantedAuthority> convert(final Jwt jwt)
    {
        Map<String, Map<String, List<String>>> authorities = jwt.getClaim(RESOURCE_ACCESS);

        if (authorities == null) return Collections.emptyList();

        return authorities.getOrDefault(RESOURCE_ID, Collections.emptyMap())
                .getOrDefault(ROLES, Collections.emptyList()).stream()
                .map(roleName -> ROLE_PREFIX + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}