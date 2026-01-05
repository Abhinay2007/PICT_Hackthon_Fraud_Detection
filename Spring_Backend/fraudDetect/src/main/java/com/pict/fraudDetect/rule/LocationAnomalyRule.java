package com.pict.fraudDetect.rule;

import com.pict.fraudDetect.model.Transaction;
import com.pict.fraudDetect.model.UserStats;
import org.springframework.stereotype.Component;

@Component
public class LocationAnomalyRule implements AnomalyRule {

    @Override
    public boolean isAnomalous(Transaction transaction, UserStats stats) {

        if (stats == null) return false;

        // Simple hackathon logic
        return transaction.getLocation() != null &&
                transaction.getLocation().equalsIgnoreCase("UNKNOWN");
    }

    @Override
    public String getType() {
        return "LOCATION_ANOMALY";
    }

    @Override
    public String getReason(Transaction transaction, UserStats stats) {
        return "Transaction location is unusual or unknown: "
                + transaction.getLocation();
    }
}
