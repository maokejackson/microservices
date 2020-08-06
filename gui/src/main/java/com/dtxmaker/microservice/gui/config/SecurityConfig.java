package com.dtxmaker.microservice.gui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    public void configure(WebSecurity web)
    {
        // @formatter:off
        web
                .ignoring()
                .antMatchers("/webjars/**");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // @formatter:off
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .oauth2Client()
        ;
        // @formatter:on
    }

//    @Bean
//    public OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clients,
//            OAuth2AuthorizedClientRepository authorizedClients)
//    {
//        OAuth2AuthorizedClientProvider authorizedClientProvider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                        .authorizationCode()
//                        .refreshToken()
//                        .build();
//
//        DefaultOAuth2AuthorizedClientManager manager = new DefaultOAuth2AuthorizedClientManager(clients,
//                authorizedClients);
//        manager.setAuthorizedClientProvider(authorizedClientProvider);
//
//        return manager;
//    }
//
//    @Bean
//    public ClientHttpRequestInterceptor authorizedClientInterceptor(OAuth2AuthorizedClientManager manager)
//    {
//        return (request, body, execution) -> {
//            Authentication principal = SecurityContextHolder.getContext().getAuthentication();
//
//            OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
//                    .withClientRegistrationId("cinema-app")
//                    .principal(principal)
//                    .build();
//            OAuth2AuthorizedClient authorizedClient = manager.authorize(authorizeRequest);
//
//            HttpHeaders headers = request.getHeaders();
//            headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
//
//            return execution.execute(request, body);
//        };
//    }

//    @Bean
//    public RestTemplate restTemplate(@Qualifier("authorizedClientInterceptor") ClientHttpRequestInterceptor interceptor)
//    {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getInterceptors().add(interceptor);
//        return restTemplate;
//    }
//
//    @Bean
//    public RestTemplateHelper restTemplateHelper(RestTemplate restTemplate)
//    {
//        return new RestTemplateHelper(restTemplate);
//    }
//
//    @Bean
//    @RequestScope
//    public RestTemplateHelper restTemplateHelper(OAuth2AuthorizedClientService clientService)
//    {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication instanceof OAuth2AuthenticationToken)
//        {
//            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//            String clientId = oauthToken.getAuthorizedClientRegistrationId();
//
//            if (clientId.equals("cinema-app"))
//            {
//                OAuth2AuthorizedClient client = clientService
//                        .loadAuthorizedClient(clientId, oauthToken.getName());
//                return new RestTemplateHelper(client.getAccessToken().getTokenValue());
//            }
//        }
//
//        return new RestTemplateHelper();
//    }
}
