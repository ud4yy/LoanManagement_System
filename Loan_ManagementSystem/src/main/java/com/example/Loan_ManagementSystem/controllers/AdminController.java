package com.example.Loan_ManagementSystem.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Loan_ManagementSystem.models.Role;
import com.example.Loan_ManagementSystem.models.User;
import com.example.Loan_ManagementSystem.repo.UserRepository;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/getOfficers/{id}")
    public List<User> getUsersByRole(@PathVariable int id) {
       
        Role userRole = Role.valueOf("LoanOfficer");
        if(id==0){
           userRole =  Role.valueOf("Customer");
            return userRepo.findByRole(userRole);
        }
        return userRepo.findByRole(userRole);
    }

    @PostMapping("/addOfficer")
    public String postMethodName() {
        return "entity";
    }
}
