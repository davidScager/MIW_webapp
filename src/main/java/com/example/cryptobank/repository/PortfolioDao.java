package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Portfolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*TODO
* in actor een check of je niet de role van beheerder hebt zo nee maak meteen een portfolio aan.
* */


@Repository
public class PortfolioDao implements Dao {



    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PortfolioDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    private PreparedStatement insertPortfolioStatement(Portfolio portfolio, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert INTO Portfolio (user) values (?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, portfolio.getUser().getId());
        return preparedStatement;
    }


    @Override
    public List list() {
        ArrayList<Portfolio> portfolioList = null;
        //portfolioList = jdbcTemplate.execute(statement -> "Select * from Portfolio");
        return portfolioList;
    }

    @Override
    public void create(Object o) {
        Portfolio portfolio = (Portfolio) o;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertPortfolioStatement(portfolio, connection), keyHolder);
        int key = keyHolder.getKey().intValue();
        portfolio.setPortfolioId(key);
    }

    @Override
    public Optional<Portfolio> get(int id) {
        Portfolio portfolio = null;
        //portfolio= jdbcTemplate.query("Select * from Portfolio where portfolioId = ?", id);
        return Optional.of(portfolio);
    }

    @Override
    public void update(Object o, int id) {
    //je kan alleen de id updaten want de user kan je niet via hier updaten dus lijkt me overbodig.
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Portfolio WHERE PortfolioId = ?", id);
    }
}
