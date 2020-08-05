package com.dtxmaker.microservice.resource.review.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>>
{
    public static final String RESOURCE_ACCESS = "resource_access";
    public static final String RESOURCE_ID     = "review-service";
    public static final String ROLES           = "roles";
    public static final String ROLE_PREFIX     = "ROLE_";

    @SuppressWarnings("unchecked")
    @Override
    public Collection<GrantedAuthority> convert(final Jwt jwt)
    {
        final Map<String, Map<String, List<String>>> resourceAccess = (Map<String, Map<String, List<String>>>) jwt
                .getClaims().get(RESOURCE_ACCESS);

        return resourceAccess.get(RESOURCE_ID).get(ROLES).stream()
                .map(roleName -> ROLE_PREFIX + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}