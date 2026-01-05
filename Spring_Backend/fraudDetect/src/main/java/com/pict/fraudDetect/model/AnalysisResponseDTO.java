package com.pict.fraudDetect.model;

import java.util.List;
import java.util.Map;

public class AnalysisResponseDTO {
    public SummaryDTO summary;
    public MetricsDTO metrics;
    public Map<String, Double> fraud_type_distribution;
    public List<FraudResultDTO> results;
}
