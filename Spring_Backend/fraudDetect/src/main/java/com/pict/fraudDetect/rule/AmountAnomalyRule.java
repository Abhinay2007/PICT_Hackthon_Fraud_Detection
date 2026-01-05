package com.pict.fraudDetect.rule;

import com.pict.fraudDetect.model.Transaction;
import com.pict.fraudDetect.model.UserStats;
import org.springframework.stereotype.Component;

@Component
public class AmountAnomalyRule implements AnomalyRule {

    @Override
    public boolean isAnomalous(Transaction transaction, UserStats stats) {

        if (stats == null) return false;

        return transaction.getAmount() > (stats.getAverageAmount() * 3);
    }

    @Override
    public String getType() {
        return "AMOUNT_ANOMALY";
    }

    @Override
    public String getReason(Transaction transaction, UserStats stats) {
        return "Transaction amount ₹" + transaction.getAmount()
                + " is significantly higher than user's average ₹"
                + stats.getAverageAmount();
    }
}
