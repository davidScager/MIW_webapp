package com.example.cryptobank.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao {

    public int getTransactionIdMostRecentTrade(String assetName);
}
