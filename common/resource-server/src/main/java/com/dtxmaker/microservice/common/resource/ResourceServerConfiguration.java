package com.dtxmaker.microservice.common.resource;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
@Configuration
@ComponentScan(basePackageClasses = ResourceServerSecurityComponents.class)
@EnableWebSecurity
public @interface ResourceServerConfiguration
{
}
