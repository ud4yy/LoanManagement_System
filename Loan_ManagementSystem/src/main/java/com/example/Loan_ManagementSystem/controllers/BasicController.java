package com.example.Loan_ManagementSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Loan_ManagementSystem.models.User;
import com.example.Loan_ManagementSystem.repo.LoanOfficerRepository;
import com.example.Loan_ManagementSystem.repo.UserRepository;
import com.example.Loan_ManagementSystem.service.RegLogin;

@RestController

public class BasicController{

    @Autowired
    LoanOfficerRepository loanOfficerRepository;
    UserRepository userRepository;

    @Autowired
    RegLogin service;

    @GetMapping("/{id}")
    public User getDetails(@RequestParam Integer id){
        return userRepository.getById(id);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        // return user;
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        //TODO: process POST request
         return service.verify(user);
    }
    
}


    

   