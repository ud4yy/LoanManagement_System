package com.example.Loan_ManagementSystem.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Loan_ManagementSystem.models.LoanOfficer;

public interface LoanOfficerRepository extends JpaRepository<LoanOfficer, Integer> {

    LoanOfficer findTopByOrderByNumberOfLoansAsc();

    
}
