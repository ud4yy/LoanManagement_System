package com.example.Loan_ManagementSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Loan_ManagementSystem.models.User;
import com.example.Loan_ManagementSystem.repo.LoanOfficerRepository;
import com.example.Loan_ManagementSystem.repo.UserRepository;

@RestController

public class BasicController{

    @Autowired
    LoanOfficerRepository loanOfficerRepository;
    UserRepository userRepository;

    @GetMapping("/{id}")
    public User getDetails(@RequestParam Integer id){
        return userRepository.getById(id);
    }

   
    

}
