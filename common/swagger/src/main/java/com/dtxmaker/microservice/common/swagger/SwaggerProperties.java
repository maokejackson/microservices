package com.dtxmaker.microservice.common.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties
{
    private String title      = "API Docs";
    private String version    = "1.0";
    private String apiPattern = "/api/.*";
}
