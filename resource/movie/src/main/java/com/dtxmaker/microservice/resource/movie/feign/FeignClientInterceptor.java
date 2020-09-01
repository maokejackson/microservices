package com.dtxmaker.microservice.resource.movie.feign;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@ConditionalOnClass(Feign.class)
public class FeignClientInterceptor implements RequestInterceptor
{
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private HttpServletRequest request;

    @Override
    public void apply(RequestTemplate template)
    {
        template.header(AUTHORIZATION_HEADER, request.getHeader(AUTHORIZATION_HEADER));
    }
}
