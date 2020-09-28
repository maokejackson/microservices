package com.dtxmaker.microservice.common.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties
{
    private String title             = "API Docs";
    private String description       = "";
    private String version           = "1.0";
    private String termsOfServiceUrl = "";

    private String license    = "MIT";
    private String licenseUrl = "https://opensource.org/licenses/MIT";

    private String[] pathPatterns = { "/api/.*" };

    private final Contact contact = new Contact();

    @Data
    public static class Contact
    {
        private String name  = "";
        private String url   = "";
        private String email = "";
    }
}
