package com.pict.fraudDetect.dto;

import java.util.List;

public class ExplanationRequestDTO {

    private String transactionId;
    private List<String> anomalyTypes;
    private List<String> ruleReasons;
    private double mlRiskScore;

    public ExplanationRequestDTO() {}

    public ExplanationRequestDTO(String transactionId,
                                 List<String> anomalyTypes,
                                 List<String> ruleReasons,
                                 double mlRiskScore) {
        this.transactionId = transactionId;
        this.anomalyTypes = anomalyTypes;
        this.ruleReasons = ruleReasons;
        this.mlRiskScore = mlRiskScore;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public List<String> getAnomalyTypes() {
        return anomalyTypes;
    }

    public List<String> getRuleReasons() {
        return ruleReasons;
    }

    public double getMlRiskScore() {
        return mlRiskScore;
    }
}
