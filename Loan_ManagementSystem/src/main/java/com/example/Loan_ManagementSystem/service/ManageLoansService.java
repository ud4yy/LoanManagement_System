package com.example.Loan_ManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Loan_ManagementSystem.models.Loan;
import com.example.Loan_ManagementSystem.models.LoanStatus;
import com.example.Loan_ManagementSystem.models.Transaction;
import com.example.Loan_ManagementSystem.models.TransactionType;
import com.example.Loan_ManagementSystem.repo.LoanRepository;
import com.example.Loan_ManagementSystem.repo.TransactionRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class ManageLoansService {
    @Autowired
    private JavaMailSender mailSender; // Add this line
 
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @Transactional
    public String updateLoan(Integer id, String status) {
        // Find the loan by ID
        Optional<Loan> loanOptional = loanRepository.findById(id);

        if (!loanOptional.isPresent()) {
            throw new IllegalArgumentException("Loan not found");
        }

        Loan loan = loanOptional.get();

        // // Update the loan status
        LoanStatus loanStatus = LoanStatus.valueOf(status);
        loan.setStatus(loanStatus);

        // // Handle the approval or rejection logic
        if (loanStatus == LoanStatus.Approved) {
            // Update balance
            loan.setBalance(loan.getAmount());

        //     // Record a disbursement transaction
            Transaction transaction = new Transaction();
            transaction.setLoan(loan);
            transaction.setAmount(loan.getAmount());
            transaction.setTransactionType(TransactionType.Disbursement);
            transaction.setTimestamp(new Date());
            transactionRepository.save(transaction);

            // Send approval email
            sendEmail("ud4yworks@gmail.com", "Loan Approved", "Your loan has been approved.");
        } else if (loanStatus == LoanStatus.Rejected) {
            // Send rejection email
            loanRepository.deleteById(id);
            sendEmail(loan.getCustomer().getUser().getEmail(), "Loan Rejected", "Your loan application has been rejected.");
            return "Loan " + loanStatus.name() + " successfully";

        }

        // // Save the updated loan
        loanRepository.save(loan);

        return "Loan " + loanStatus.name() + " successfully";
        // return "hello";
    }

    private void sendEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
