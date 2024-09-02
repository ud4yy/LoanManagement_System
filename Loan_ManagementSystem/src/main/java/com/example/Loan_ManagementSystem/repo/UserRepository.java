package com.example.Loan_ManagementSystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Loan_ManagementSystem.models.Role;
import com.example.Loan_ManagementSystem.models.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByRole(Role role);

}
