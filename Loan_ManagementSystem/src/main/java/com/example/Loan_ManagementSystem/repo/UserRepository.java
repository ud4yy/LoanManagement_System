package com.example.Loan_ManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Loan_ManagementSystem.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
