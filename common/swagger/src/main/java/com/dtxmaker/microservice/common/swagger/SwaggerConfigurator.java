package com.dtxmaker.microservice.common.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class SwaggerConfigurator
{
    public static final String API_URL = "/v3/api-docs";

    public static final String[] RESOURCES = {
            // -- swagger ui
            "/api",
            API_URL,
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

    @Bean
    public Docket restApi()
    {
        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .genericModelSubstitutes(genericModelSubstitutes())
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                .useDefaultResponseMessages(false);

        return docket.select()
                .paths(path -> Arrays.stream(properties.getPathPatterns()).anyMatch(path::matches))
                .build();
    }

    protected Class<?>[] genericModelSubstitutes()
    {
        return new Class[] { ResponseEntity.class };
    }

    protected List<SecurityContext> securityContexts()
    {
        return Collections.singletonList(jwtContext());
    }

    protected List<SecurityScheme> securitySchemes()
    {
        return Collections.singletonList(jwtScheme());
    }

    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .termsOfServiceUrl(properties.getTermsOfServiceUrl())
                .contact(contact())
                .license(properties.getLicense())
                .licenseUrl(properties.getLicenseUrl())
                .build();
    }

    private Contact contact()
    {
        return new Contact(
                properties.getContact().getName(),
                properties.getContact().getUrl(),
                properties.getContact().getEmail()
        );
    }

    private SecurityScheme jwtScheme()
    {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER
                .name("JWT")
                .build();
    }

    private SecurityContext jwtContext()
    {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(jwtReference()))
                .operationSelector(context -> Arrays.stream(properties.getPathPatterns())
                        .anyMatch(pattern -> context.requestMappingPattern().matches(pattern)))
                .build();
    }

    private SecurityReference jwtReference()
    {
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = { scope };
        return new SecurityReference("JWT", scopes);
    }
}
