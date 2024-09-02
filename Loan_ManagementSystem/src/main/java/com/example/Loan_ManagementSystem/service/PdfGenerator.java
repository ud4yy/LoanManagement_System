package com.example.Loan_ManagementSystem.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse; // Use javax.servlet package

import com.example.Loan_ManagementSystem.models.Transaction;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

@Service
public class PdfGenerator {

    public void generate(List<Transaction> transactionList, HttpServletResponse response) 
            throws DocumentException, IOException {
        // Create a new document with A4 page size
        Document document = new Document(PageSize.A4);
        
        // Get the PDF writer instance
        PdfWriter.getInstance(document, response.getOutputStream());
        
        // Open the document to make changes
        document.open();
        
        // Create a font for the title
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        
        // Create a title paragraph
        Paragraph paragraph = new Paragraph("List of Transactions", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        
        // Add the title paragraph to the document
        document.add(paragraph);
        
        // Create a table with the appropriate number of columns
        PdfPTable table = new PdfPTable(4); // Adjust the number of columns as needed
        
        // Set the table width and column width percentages
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 3, 3, 3});
        table.setSpacingBefore(5);
        
        // Create table header cells
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(CMYKColor.BLUE);
        cell.setPadding(5);
        
        // Set header font style and color
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontHeader.setColor(CMYKColor.WHITE);
        
        // Add headers to the table
        cell.setPhrase(new Phrase("Transaction ID", fontHeader));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Transaction Date", fontHeader));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Amount", fontHeader));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Transaction Type", fontHeader));
        table.addCell(cell);
        
        // Iterate over the transaction list and add rows to the table
        for (Transaction transaction : transactionList) {
            table.addCell(String.valueOf(transaction.getLoan().getLoanId()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

// Format the date
            String formattedDate = dateFormat.format(transaction.getTimestamp());
            table.addCell(formattedDate);
            table.addCell(String.valueOf(transaction.getAmount()));
            table.addCell(transaction.getTransactionType().toString());
        }
        
        // Add the table to the document
        document.add(table);
        
        // Close the document
        document.close();
    }
}
