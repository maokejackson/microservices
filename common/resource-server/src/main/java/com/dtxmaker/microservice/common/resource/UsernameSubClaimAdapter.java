package com.dtxmaker.microservice.common.resource;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;

import java.util.Collections;
import java.util.Map;

public class UsernameSubClaimAdapter implements Converter<Map<String, Object>, Map<String, Object>>
{
    private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

    private final String principalAttribute;

    public UsernameSubClaimAdapter(String principalAttribute)
    {
        this.principalAttribute = principalAttribute;
    }

    @Override
    public Map<String, Object> convert(Map<String, Object> claims)
    {
        Map<String, Object> convertedClaims = delegate.convert(claims);

        String username = (String) convertedClaims.get(principalAttribute);
        convertedClaims.put("sub", username);

        return convertedClaims;
    }
}

