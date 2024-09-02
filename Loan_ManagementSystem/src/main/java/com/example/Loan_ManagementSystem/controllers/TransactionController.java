package com.example.Loan_ManagementSystem.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Loan_ManagementSystem.models.Transaction;
import com.example.Loan_ManagementSystem.repo.TransactionRepository;
import com.example.Loan_ManagementSystem.service.PdfGenerator;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse; // Use javax.servlet package

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PdfGenerator pdfGenerator;
    

    @GetMapping("/getTransactions/{loanid}")
    public void generatePdfFile(@PathVariable Integer loanid, HttpServletResponse response) 
            throws DocumentException, IOException {
        // Set response content type
        response.setContentType("application/pdf");
        
        // Format the current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        
        // Set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        
        // Fetch transactions by loan ID
        List<Transaction> listOfTransactions = transactionRepository.findByLoan_LoanId(loanid);
        
        // Generate PDF using the PdfGenerator
        pdfGenerator.generate(listOfTransactions, response);
    }
}
