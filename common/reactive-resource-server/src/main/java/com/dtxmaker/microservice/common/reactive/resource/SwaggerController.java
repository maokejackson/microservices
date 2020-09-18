package com.dtxmaker.microservice.common.reactive.resource;

import com.dtxmaker.microservice.common.swagger.SwaggerProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController
{
    @Autowired
    private SwaggerProperties properties;

    @GetMapping("/api")
    public String api(Model model)
    {
        model.addAttribute("title", properties.getTitle());
        return "api";
    }
}
