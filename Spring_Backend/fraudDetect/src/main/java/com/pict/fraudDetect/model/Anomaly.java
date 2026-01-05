package com.pict.fraudDetect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "anomalies")
public class Anomaly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "anomaly_type", nullable = false)
    private String anomalyType;
    // Example: HIGH_AMOUNT, HIGH_FREQUENCY, LOCATION_MISMATCH

    @Column(length = 500)
    private String reason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ---- Constructors ----
    public Anomaly() {}

    public Anomaly(String transactionId, String anomalyType, String reason) {
        this.transactionId = transactionId;
        this.anomalyType = anomalyType;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
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

    public String getAnomalyType() {
        return anomalyType;
    }

    public void setAnomalyType(String anomalyType) {
        this.anomalyType = anomalyType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
