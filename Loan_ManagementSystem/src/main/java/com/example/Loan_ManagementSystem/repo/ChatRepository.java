package com.example.Loan_ManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Loan_ManagementSystem.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}

