package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao {

    public double getTradedRateByTransactionId(int transactionId);

    void saveLog(Transaction transaction);

}
