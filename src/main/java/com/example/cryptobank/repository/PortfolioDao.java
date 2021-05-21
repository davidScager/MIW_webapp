package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Portfolio;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioDao {

    public List list();

    public void create(Portfolio portfolio);

    public Optional<Portfolio> get(int id);

    public void delete(int id);

    public Portfolio getPortfolioIdByUserId(int userId);
}
