package com.pict.fraudDetect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String location;

    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime transactionTime;

    // ---- Constructors ----
    public Transaction() {}

    public Transaction(String transactionId, String userId, Double amount,
                       String location, LocalDateTime transactionTime) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.location = location;
        this.transactionTime = transactionTime;
    }

    // ---- Getters & Setters ----
    public Long getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}
