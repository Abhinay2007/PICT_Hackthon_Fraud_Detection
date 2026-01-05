package com.pict.fraudDetect.ml;

import com.pict.fraudDetect.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class MlModelClient {

    public MlResult predict(Transaction transaction) {

        // 🔹 MOCK LOGIC (replace with real ML API later)
        double riskScore;

        if (transaction.getAmount() > 50000) {
            riskScore = 0.9;   // High risk
        } else if (transaction.getAmount() > 20000) {
            riskScore = 0.6;   // Medium risk
        } else {
            riskScore = 0.2;   // Low risk
        }

        return new MlResult(riskScore);
    }
}
