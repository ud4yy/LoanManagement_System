package com.example.Loan_ManagementSystem.models;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LoanOfficers")
public class LoanOfficer {
    
    @Id
    private Integer userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    @Column(columnDefinition = "json")
    private String assignedLoans;

    @Column(name="numberOfLoans")
    private Integer numberOfLoans; // or the property you are actually using

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Getters and Setters
}
