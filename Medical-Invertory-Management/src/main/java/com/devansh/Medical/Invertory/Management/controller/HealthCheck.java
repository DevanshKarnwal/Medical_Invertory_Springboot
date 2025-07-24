package com.devansh.Medical.Invertory.Management.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class HealthCheck {
    @GetMapping("health")
    public String healthCheck(){
        return "Working";
    }
}
