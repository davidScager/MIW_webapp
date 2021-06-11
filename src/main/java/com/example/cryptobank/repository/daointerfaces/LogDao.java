package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.transaction.Transaction;
import com.example.cryptobank.domain.transaction.TransactionLog;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao {

    public double getTradedRateByTransactionId(int transactionId);

    public TransactionLog getTransactionLogByTransactionId(int transactionId);

    void saveLog(Transaction transaction);

}
