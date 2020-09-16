package com.dtxmaker.microservice.common.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

public abstract class SwaggerConfigurator
{
    public static final String[] RESOURCES = {
            // -- swagger ui
            "/api",
            "/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };

    @Autowired
    private SwaggerProperties properties;

    protected abstract Class<?>[] genericModelSubstitutes();

    @Bean
    public Docket restApi()
    {
        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .genericModelSubstitutes(genericModelSubstitutes())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .useDefaultResponseMessages(false);

        return docket.select()
                .paths(PathSelectors.regex(properties.getApiPattern()))
                .build();
    }

    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .version(properties.getVersion())
                .build();
    }

    private ApiKey apiKey()
    {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext()
    {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(context -> context.requestMappingPattern().matches(properties.getApiPattern()))
                .build();
    }

    private List<SecurityReference> defaultAuth()
    {
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = { scope };
        return Collections.singletonList(new SecurityReference("JWT", scopes));
    }
}
