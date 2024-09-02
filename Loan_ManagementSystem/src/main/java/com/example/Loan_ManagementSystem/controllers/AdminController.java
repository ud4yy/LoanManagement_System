package com.example.Loan_ManagementSystem.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Loan_ManagementSystem.models.Role;
import com.example.Loan_ManagementSystem.models.User;
import com.example.Loan_ManagementSystem.repo.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    UserRepository userRepo;

    @GetMapping("/getOfficers")
    
    public List<User> getUsersByRole(Role role) {
        return userRepo.findByRole(role);
    }
    

    @PostMapping("/addOfficer")
    public String postMethodName() {
        //TODO: process POST request
        
        return "entity";
    }
    
}
