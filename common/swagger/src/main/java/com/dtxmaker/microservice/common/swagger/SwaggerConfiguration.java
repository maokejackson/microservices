package com.dtxmaker.microservice.common.swagger;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
@Configuration
@ComponentScan(basePackageClasses = SwaggerComponents.class)
public @interface SwaggerConfiguration
{
}
