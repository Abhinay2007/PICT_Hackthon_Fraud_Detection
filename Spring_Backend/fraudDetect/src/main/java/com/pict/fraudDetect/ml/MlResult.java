package com.pict.fraudDetect.ml;

public class MlResult {

    private double riskScore;

    public MlResult() {}

    public MlResult(double riskScore) {
        this.riskScore = riskScore;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }
}
