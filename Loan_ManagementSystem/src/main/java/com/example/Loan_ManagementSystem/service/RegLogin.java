package com.example.Loan_ManagementSystem.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Loan_ManagementSystem.models.User;
import com.example.Loan_ManagementSystem.repo.UserRepository;

@Service
public class RegLogin {

    @Autowired
    JWTService jwt;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repo;

    public User register(User user) {
        String rawPassword = user.getPassword();
    
        user.setPassword(rawPassword);
        user.setCreatedAt(new Date());
        repo.save(user);
        return user;
    }

    public String verify(User user) {
       
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
                );
           
                if(authentication.isAuthenticated()){
                 return jwt.generateToken(user.getName());
                }
            return "Not authenticated";
    }
}
