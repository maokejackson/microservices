package com.dtxmaker.microservice.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GuiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(GuiApplication.class, args);
    }
}
