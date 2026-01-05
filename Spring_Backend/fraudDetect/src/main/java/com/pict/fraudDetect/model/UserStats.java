package com.pict.fraudDetect.model;

public class UserStats {

    private String userId;
    private Double averageAmount;
    private Double minAmount;
    private Double maxAmount;
    private Long transactionCount;

    // ---- Constructors ----
    public UserStats() {}

    public UserStats(String userId, Double averageAmount,
                     Double minAmount, Double maxAmount,
                     Long transactionCount) {
        this.userId = userId;
        this.averageAmount = averageAmount;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.transactionCount = transactionCount;
    }

    // ---- Getters & Setters ----
    public String getUserId() {
        return userId;
    }

    public Double getAverageAmount() {
        return averageAmount;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public Long getTransactionCount() {
        return transactionCount;
    }
}

