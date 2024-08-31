package com.example.Loan_ManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Loan_ManagementSystem.models.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
