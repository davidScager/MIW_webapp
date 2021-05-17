package com.example.cryptobank.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface LogDao {

    public double getTradedRateByTransactionId(int transactionId);

}
