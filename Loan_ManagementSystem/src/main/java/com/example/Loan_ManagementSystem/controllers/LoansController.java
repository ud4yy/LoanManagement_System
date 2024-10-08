package com.example.Loan_ManagementSystem.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import com.example.Loan_ManagementSystem.models.Loan;
import com.example.Loan_ManagementSystem.repo.LoanRepository;
import com.example.Loan_ManagementSystem.service.GetAssignedLoansService;
import com.example.Loan_ManagementSystem.service.LoanApplyService;
import com.example.Loan_ManagementSystem.service.LoanRepaymentService;
import com.example.Loan_ManagementSystem.service.ManageLoansService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class StatusDTO {
    private Integer id;
    private String status;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class RepayDTO{
    private Integer id;
    private Double amount;
}


@RestController
@RequestMapping("/loans")
public class LoansController {

    @Autowired
    LoanRepository loanrepo;

    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private GetAssignedLoansService assignedLoans;

    @Autowired
    LoanRepaymentService loanRepaymentService;
    
    @Autowired
    private ManageLoansService manageloans;
    
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

    @GetMapping("/assigned/{id}")
    public ResponseEntity<List<Loan>> viewAssignedLoans(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(assignedLoans.getLoans(id));
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(assignedLoans.getLoans(id));
        }
    }


    @PostMapping("/modifyStatus")
    public String modifyStatus(@RequestBody StatusDTO statusDTO) {
        Integer id = statusDTO.getId();
        String status = statusDTO.getStatus();
        // TODO: process POST request
        return manageloans.updateLoan(id,status);
    }

    @PostMapping("/repay")
    public ResponseEntity<String>repayLoan(@RequestBody RepayDTO repay) {
        try{
            String ans = loanRepaymentService.repayLoan(repay.getId(),repay.getAmount());
            return ResponseEntity.ok(ans);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(loanRepaymentService.repayLoan(repay.getId(),repay.getAmount()));
        }
    }
    
    @GetMapping("/getdetails/{id}")
    public ResponseEntity<Loan> getDetails(Integer loanid) {
        try{
            Loan loan = loanrepo.getById(loanid);
            return ResponseEntity.ok(loan);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(loanrepo.getById(loanid));
        }
    }
    
}
