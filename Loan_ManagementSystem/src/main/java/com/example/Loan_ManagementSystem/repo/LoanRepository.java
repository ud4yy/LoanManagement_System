package com.example.Loan_ManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Loan_ManagementSystem.models.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
