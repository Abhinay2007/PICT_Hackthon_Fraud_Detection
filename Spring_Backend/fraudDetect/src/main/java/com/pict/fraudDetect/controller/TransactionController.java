package com.pict.fraudDetect.controller;

import com.pict.fraudDetect.model.Transaction;
import com.pict.fraudDetect.service.TransactionServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionServiceImplementation transactionService;

    // Get all transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // Get transactions by user
    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(@PathVariable String userId) {
        return transactionService.getTransactionsByUser(userId);
    }
}
