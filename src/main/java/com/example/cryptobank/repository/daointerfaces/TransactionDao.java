package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDao {

    public List<Transaction> getTransactionsForUser(int userId);

//    public List<Transaction> getSellsForAsset(String assetName);

    public void saveTransaction(Transaction transaction);

    public double calculateTransactionCost(double dollarAmount);

    public void delete(int id);

}
