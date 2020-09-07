package com.dtxmaker.microservice.resource.review.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
public class SwaggerConfig
{
    private static final String AUTHORIZATION_HEADER    = "Authorization";
    private static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    @Bean
    public Docket restApi()
    {
        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false);

        return docket.select()
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title("Review Service API")
                .description("Review Service API")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey()
    {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext()
    {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    private List<SecurityReference> defaultAuth()
    {
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = { scope };
        return Lists.newArrayList(new SecurityReference("JWT", scopes));
    }
}
