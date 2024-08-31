package com.example.Loan_ManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Loan_ManagementSystem.models.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
