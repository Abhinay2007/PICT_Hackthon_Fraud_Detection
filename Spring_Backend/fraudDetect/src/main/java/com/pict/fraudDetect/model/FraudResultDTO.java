package com.pict.fraudDetect.model;

public class FraudResultDTO {
    public String transaction_id;
    public String user_id;
    public double amount;
    public String location;
    public String risk_assessment;
    public boolean is_fraud;
}
