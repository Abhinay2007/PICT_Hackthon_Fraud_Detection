package com.pict.fraudDetect.rule;

import com.pict.fraudDetect.model.Transaction;
import com.pict.fraudDetect.model.UserStats;

public interface AnomalyRule {

    boolean isAnomalous(Transaction transaction, UserStats userStats);

    String getType();

    String getReason(Transaction transaction, UserStats userStats);
}
