package com.pict.fraudDetect.rule;

import com.pict.fraudDetect.model.Transaction;
import com.pict.fraudDetect.model.UserStats;
import org.springframework.stereotype.Component;

@Component
public class FrequencyAnomalyRule implements AnomalyRule {

    private static final long MAX_TXNS_PER_USER = 10;

    @Override
    public boolean isAnomalous(Transaction transaction, UserStats stats) {
        if (stats == null) return false;

        return stats.getTransactionCount() > MAX_TXNS_PER_USER;
    }

    @Override
    public String getType() {
        return "FREQUENCY_ANOMALY";
    }

    @Override
    public String getReason(Transaction transaction, UserStats stats) {
        return "User has made " + stats.getTransactionCount()
                + " transactions in a short period, exceeding normal behavior";
    }
}
