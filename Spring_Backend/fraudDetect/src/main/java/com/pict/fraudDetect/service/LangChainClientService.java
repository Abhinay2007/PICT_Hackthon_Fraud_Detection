package com.pict.fraudDetect.service;

import com.pict.fraudDetect.dto.ExplanationRequestDTO;
import com.pict.fraudDetect.dto.ExplanationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class LangChainClientService {

    public ExplanationResponseDTO explain(ExplanationRequestDTO request) {

        // Mocked explanation for hackathon
        String explanation =
                "Transaction " + request.getTransactionId() +
                        " was flagged due to " + String.join(", ", request.getAnomalyTypes()) +
                        ". Reasons include: " + String.join("; ", request.getRuleReasons()) +
                        ". ML risk score: " + request.getMlRiskScore() + ".";

        String confidence =
                request.getMlRiskScore() > 0.8 ? "High" : "Medium";

        return new ExplanationResponseDTO(explanation, confidence);
    }
}
