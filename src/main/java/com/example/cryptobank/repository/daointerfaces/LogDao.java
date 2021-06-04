package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.domain.TransactionLog;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao {

    public double getTradedRateByTransactionId(int transactionId);

    public TransactionLog getTransactionLogByTransactionId(int transactionId);

    void saveLog(Transaction transaction);

}
