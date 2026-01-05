package com.pict.fraudDetect.service;

import com.pict.fraudDetect.model.Transaction;
import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByUser(String userId);
}
