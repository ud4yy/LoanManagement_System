package com.example.Loan_ManagementSystem.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class Home {
        
    @GetMapping("/")
    public String thisisHome() {
        return new String("This is home");
    }
}
