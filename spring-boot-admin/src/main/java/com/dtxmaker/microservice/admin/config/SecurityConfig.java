package com.dtxmaker.microservice.admin.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.csrf.CookieCsrfTokenRepository.withHttpOnlyFalse;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    private final AdminServerProperties adminServer;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers(adminServer.path("/assets/**")).permitAll()
                        .requestMatchers(adminServer.path("/login")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(customizer -> customizer
                        .loginPage(adminServer.path("/login"))
                        .defaultSuccessUrl(adminServer.path("/"), true)
                )
                .logout(customizer -> customizer.logoutUrl(adminServer.path("/logout")))
                .httpBasic(withDefaults())
                .csrf(customizer -> customizer
                        .csrfTokenRepository(withHttpOnlyFalse())
                        .ignoringRequestMatchers(
                                new AntPathRequestMatcher(adminServer.path("/instances"), POST.toString()),
                                new AntPathRequestMatcher(adminServer.path("/instances/*"), DELETE.toString()),
                                new AntPathRequestMatcher(adminServer.path("/actuator/**")))
                )
                .build();
    }
}
