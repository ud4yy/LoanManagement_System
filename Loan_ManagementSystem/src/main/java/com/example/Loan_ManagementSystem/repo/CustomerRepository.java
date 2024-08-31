package com.example.Loan_ManagementSystem.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Loan_ManagementSystem.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    
}
