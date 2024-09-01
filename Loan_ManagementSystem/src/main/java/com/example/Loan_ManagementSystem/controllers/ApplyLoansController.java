package com.example.Loan_ManagementSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Loan_ManagementSystem.service.LoanApplyService;

@RestController
@RequestMapping("/loans")
public class ApplyLoansController {

    @Autowired
    private LoanApplyService loanApplyService;

    @PostMapping("/apply")
    public ResponseEntity<String> applyLoan(@RequestBody LoanReq loanReq ) {
        try {
            
            String result = loanApplyService.apply(loanReq.customerId, loanReq.amount);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while applying for the loan: " + e.getMessage());
        }
    }
}
