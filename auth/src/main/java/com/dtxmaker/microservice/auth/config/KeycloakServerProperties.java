package com.dtxmaker.microservice.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "keycloak.server")
public class KeycloakServerProperties
{
    private String    contextPath;
    private String    realmImportFile;
    private AdminUser adminUser = new AdminUser();

    @Data
    public static class AdminUser
    {
        private String username;
        private String password;
    }
}
