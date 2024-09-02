package com.example.Loan_ManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Loan_ManagementSystem.models.Loan;
import com.example.Loan_ManagementSystem.models.LoanOfficer;
import com.example.Loan_ManagementSystem.repo.LoanOfficerRepository;
import com.example.Loan_ManagementSystem.repo.LoanRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GetAssignedLoansService {

    @Autowired
    private LoanOfficerRepository loanOfficerRepository;

    @Autowired
    private LoanRepository loanRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Loan> getLoans(Integer loanOfficerId) {
        // Fetch the LoanOfficer by ID
        LoanOfficer officer = loanOfficerRepository.findById(loanOfficerId)
            .orElseThrow(() -> new IllegalArgumentException("Loan Officer not found"));

        List<Loan> loans = new ArrayList<>();

        try {
    
            Map<String, String> assignedLoans = objectMapper.readValue(
                officer.getAssignedLoans(),
                new TypeReference<Map<String, String>>() {}
            );

            // Iterate over the loan IDs and fetch the corresponding Loan objects
            for (String loanId : assignedLoans.keySet()) {
                Loan loan = loanRepository.findById(Integer.parseInt(loanId))
                    .orElseThrow(() -> new IllegalArgumentException("Loan not found: " + loanId));
                loan.setLoanOfficer(null);

                loans.add(loan);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve assigned loans", e);
        }

        return loans;
    }
}
