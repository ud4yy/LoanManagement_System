package com.example.Loan_ManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Loan_ManagementSystem.models.Loan;
import com.example.Loan_ManagementSystem.models.Transaction;
import com.example.Loan_ManagementSystem.models.TransactionType;
import com.example.Loan_ManagementSystem.repo.LoanRepository;
import com.example.Loan_ManagementSystem.repo.TransactionRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class LoanRepaymentService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public String repayLoan(Integer id, Double amount) {
        // Find the loan by ID
        Optional<Loan> loanOptional = loanRepository.findById(id);

        if (!loanOptional.isPresent()) {
            throw new IllegalArgumentException("Loan not found");
        }

        Loan loan = loanOptional.get();

        // Check if repayment amount is valid
        if (amount <= 0) {
            throw new IllegalArgumentException("Repayment amount must be greater than zero");
        }

        // Update loan balance
        Double newBalance = loan.getBalance() - amount;
        if (newBalance < 0) {
            throw new IllegalArgumentException("Repayment amount exceeds loan balance");
        }
        loan.setBalance(newBalance);

        // Record a repayment transaction
        Transaction transaction = new Transaction();
        transaction.setLoan(loan);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.Payment);
        transaction.setTimestamp(new Date());
        transactionRepository.save(transaction);

        // Save the updated loan
        loanRepository.save(loan);
        return "Loan repayment processed successfully";
    }
}
