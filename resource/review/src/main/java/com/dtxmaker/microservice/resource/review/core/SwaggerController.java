package com.dtxmaker.microservice.resource.review.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController
{
    @GetMapping("/api")
    public String api()
    {
        return "api";
    }
}
