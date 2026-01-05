package com.pict.fraudDetect.repository;

import com.pict.fraudDetect.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    // Fetch all transactions of a user
    List<Transaction> findByUserId(String userId);
}
