package com.example.Loan_ManagementSystem.controllers;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanReq {
    Integer customerId;
    Double amount;
}
