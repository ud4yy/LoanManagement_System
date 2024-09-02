package com.example.Loan_ManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Loan_ManagementSystem.models.Customer;
import com.example.Loan_ManagementSystem.models.Loan;
import com.example.Loan_ManagementSystem.models.LoanOfficer;
import com.example.Loan_ManagementSystem.models.LoanStatus;
import com.example.Loan_ManagementSystem.repo.LoanOfficerRepository;
import com.example.Loan_ManagementSystem.repo.LoanRepository;
import com.example.Loan_ManagementSystem.repo.CustomerRepository; // Ensure you have a repository to find customer
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoanApplyService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanOfficerRepository loanOfficerRepository;

    @Autowired
    private CustomerRepository customerRepository; // For fetching customer details

    private ObjectMapper objectMapper = new ObjectMapper();

    private LoanOfficer getLoanOfficerWithLeastLoans() {
        return loanOfficerRepository.findTopByOrderByNumberOfLoansAsc();
    }
    public String apply(Integer customerId, Double amount) {
        // Fetch customer details using customerId
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    
        // Create a new Loan object
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setAmount(amount);
        loan.setStatus(LoanStatus.Pending);
        loan.setBalance(0.0);
        // Fetch the loan officer with the least number of loans
        LoanOfficer officer = getLoanOfficerWithLeastLoans();
        if (officer == null) {
            return "No Loan Officers available";
        }
    
        // Assign the loan officer to the loan
        loan.setLoanOfficer(officer);
    
        // Increment the number of loans assigned to the officer
        officer.setNumberOfLoans(officer.getNumberOfLoans() + 1);
        loan.setRate(5.0);
    
        loanRepository.save(loan);
    
        // Update assigned loans with loan ID and customer ID
        officer = updateAssignedLoans(officer, loan);
    
        // Save the updated officer details
        loanOfficerRepository.save(officer);
    
        return "Loan applied successfully";
    }
    
    //Assigning loans to loanofficer
    private LoanOfficer updateAssignedLoans(LoanOfficer officer, Loan loan) {
        try {
            // Handle null case for assignedLoans
            Map<String, String> assignedLoans = new HashMap<>();
            if (officer.getAssignedLoans() != null) {
                assignedLoans = objectMapper.readValue(
                    officer.getAssignedLoans(),
                    new TypeReference<Map<String, String>>() {}
                );
            }

            // Ensure loan.getCustomer() is not null before accessing its methods
            if (loan.getCustomer() == null || loan.getLoanId() == null) {
                throw new IllegalArgumentException("Loan or Customer details are incomplete");
            }

            // Add the new loan ID and customer ID to the map
            assignedLoans.put(loan.getLoanId().toString(), loan.getCustomer().getUserId().toString());

            // Convert the map back to a JSON string
            String updatedJson = objectMapper.writeValueAsString(assignedLoans);

            // Set the updated JSON string to the officer
            officer.setAssignedLoans(updatedJson);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update assigned loans JSON", e);
        }

        return officer;
    }
}
