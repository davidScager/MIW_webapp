package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.AssetPortfolio;
import com.example.cryptobank.domain.AssetPortfolioView;
import com.example.cryptobank.domain.Portfolio;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AssetPortfolioDao {

    public List<Asset> getAssetOverview(int portfolioId);

    public Map<Asset, Double> getAssetOverviewWithAmount(int portfolioId);

    public List<AssetPortfolioView> getOverviewWithAmount(int portfolioId);

    public double getAmountByAssetName(String name, int portfolioId);

    public void update(Asset asset, Portfolio portfolio, double amount);

    public void delete(int id);

    public void create(AssetPortfolio assetPortfolio);

    /*public void update(AssetPortfolio assetPortfolio);*/



}
