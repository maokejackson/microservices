package com.dtxmaker.microservice.common.resource;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>>
{
    public static final String ROLES_CLAIM = "roles";
    public static final String ROLE_PREFIX = "ROLE_";

    @Override
    public Collection<GrantedAuthority> convert(final Jwt jwt)
    {
        if (!jwt.containsClaim(ROLES_CLAIM))
        {
            return Collections.emptyList();
        }

        final List<String> roles = jwt.getClaim(ROLES_CLAIM);

        return roles.stream()
                .map(roleName -> ROLE_PREFIX + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}