package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.asset.AssetPortfolio;
import com.example.cryptobank.domain.asset.AssetPortfolioView;
import com.example.cryptobank.domain.portfolio.Portfolio;
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

    public void checkExistElseCreate(Asset asset, Portfolio portfolio);

    public AssetPortfolio getAssetPortfolioId(Asset asset, Portfolio portfolio);

    public void delete(int id);

    void updateAssetsForSale(String Symbol, int portfolioId, double forSale);

    public void create(AssetPortfolio assetPortfolio);

    public List<AssetPortfolio> getAssetPortfolioByAbbrevation(String symbol);

    /*public void update(AssetPortfolio assetPortfolio);*/



}
