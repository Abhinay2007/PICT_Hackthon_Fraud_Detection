package com.pict.fraudDetect.service;

import com.pict.fraudDetect.model.Transaction;
import com.pict.fraudDetect.model.UserStats;
import com.pict.fraudDetect.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatsServiceImplementation
        implements UserStatsService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public UserStats calculateUserStats(String userId) {

        List<Transaction> txns =
                transactionRepository.findByUserId(userId);

        if (txns.isEmpty()) return null;

        double sum = 0, min = Double.MAX_VALUE, max = Double.MIN_VALUE;

        for (Transaction t : txns) {
            sum += t.getAmount();
            min = Math.min(min, t.getAmount());
            max = Math.max(max, t.getAmount());
        }

        return new UserStats(
                userId,
                sum / txns.size(),
                min,
                max,
                (long) txns.size()
        );
    }
}
