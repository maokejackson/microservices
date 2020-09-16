package com.dtxmaker.microservice.common.resource;

import com.dtxmaker.microservice.common.swagger.SwaggerConfiguration;
import com.dtxmaker.microservice.common.swagger.SwaggerConfigurator;

import org.springframework.http.ResponseEntity;

@SwaggerConfiguration
public class SwaggerConfig extends SwaggerConfigurator
{
    @Override
    protected Class<?>[] genericModelSubstitutes()
    {
        return new Class[] { ResponseEntity.class };
    }
}
