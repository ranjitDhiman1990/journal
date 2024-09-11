package com.drjava.journal.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HealthCheck {

    @GetMapping("/health-check")
    public String getMethodName() {
        return "OK";
    }
}
