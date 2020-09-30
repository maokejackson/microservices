package com.dtxmaker.microservice.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final AdminServerProperties adminServer;

    public SecurityConfig(AdminServerProperties adminServer)
    {
        this.adminServer = adminServer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers(adminServer.path("/assets/**")).permitAll()
                .antMatchers(adminServer.path("/login")).permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage(adminServer.path("/login"))
                .defaultSuccessUrl(adminServer.path("/"), true)
                .and()
            .logout()
                .logoutUrl(adminServer.path("/logout"))
                .and()
            .httpBasic()
                .and()
            .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers(
                        new AntPathRequestMatcher(adminServer.path("/instances"), HttpMethod.POST.toString()),
                        new AntPathRequestMatcher(adminServer.path("/instances/*"), HttpMethod.DELETE.toString()),
                        new AntPathRequestMatcher(adminServer.path("/actuator/**")))
                .and()
            .rememberMe()
                .key(UUID.randomUUID().toString())
                .tokenValiditySeconds(1209600)  // 14 days
        ;
        // @formatter:on
    }
}
