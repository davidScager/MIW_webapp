package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao {

    public int getTransactionIdMostRecentTrade(String assetName);

    public void saveTransaction(Transaction transaction);

    public double calculateTransactionCost(double dollarAmount);

    public void delete(int id);

}
