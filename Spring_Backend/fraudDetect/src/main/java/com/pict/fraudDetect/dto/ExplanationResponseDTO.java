package com.pict.fraudDetect.dto;

public class ExplanationResponseDTO {

    private String explanation;
    private String confidenceLevel;

    public ExplanationResponseDTO() {}

    public ExplanationResponseDTO(String explanation, String confidenceLevel) {
        this.explanation = explanation;
        this.confidenceLevel = confidenceLevel;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getConfidenceLevel() {
        return confidenceLevel;
    }
}
