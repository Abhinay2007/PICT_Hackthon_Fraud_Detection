package com.pict.fraudDetect.dto;

import java.util.List;

public class AnomalyResultDTO {

    private String transactionId;
    private List<String> anomalyTypes;
    private List<String> reasons;
    private double mlRiskScore;

    public AnomalyResultDTO() {}

    public AnomalyResultDTO(String transactionId,
                            List<String> anomalyTypes,
                            List<String> reasons,
                            double mlRiskScore) {
        this.transactionId = transactionId;
        this.anomalyTypes = anomalyTypes;
        this.reasons = reasons;
        this.mlRiskScore = mlRiskScore;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public List<String> getAnomalyTypes() {
        return anomalyTypes;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public double getMlRiskScore() {
        return mlRiskScore;
    }
}
