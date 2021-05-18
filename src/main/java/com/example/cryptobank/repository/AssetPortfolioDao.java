package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Portfolio;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetPortfolioDao {

    public List<Asset> getAssetOverview(int portfolioId);

    public double getAmountByAssetName(String name, int portfolioId);

    public void create(Object o);

    public void update(Asset asset, Portfolio portfolio, double amount);

    public void delete(int id);
}
