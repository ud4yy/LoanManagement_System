package com.example.Loan_ManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Loan_ManagementSystem.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
