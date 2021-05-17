package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetPortfolioDao {

    public List<Asset> getAssetOverview(int portfolioId);

    public double getAmountByAssetName(String name, int portfolioId);

    public void create(Object o);

    public void update(Object o, int id);

    public void delete(int id);
}
