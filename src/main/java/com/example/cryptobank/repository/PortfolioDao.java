package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Actor;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

/*TODO
* in actor een check of je niet de role van beheerder hebt zo nee maak meteen een portfolio aan.
* actordao inplementeren
* */


@Repository
public class PortfolioDao implements Dao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PortfolioDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }
    RowMapper<Portfolio> portfolioRowMapper = (resultSet, rownumber) -> {Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId(resultSet.getInt("portfolioId"));
        /*Actor actor = actorDao.get(resultSet.getInt("user"));
        portfolio.setActor(actor);*/
        return portfolio;
    };

    private PreparedStatement insertPortfolioStatement(Portfolio portfolio, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert INTO Portfolio (user) values (?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, portfolio.getActor().getUserId());
        return preparedStatement;
    }


    @Override
    public List list() {
        return jdbcTemplate.query("Select * from Portfolio", portfolioRowMapper);
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
    public Optional<Portfolio> get(int id) throws MissingResourceException {
        List<Portfolio> portfolioList = jdbcTemplate.query("Select * from Portfolio where portfolioId = ?", portfolioRowMapper,id);
        if (portfolioList.size() == 1) {
            return Optional.of(portfolioList.get(0));
        }
        throw new MissingResourceException("No such Portfolio", "Portfolio", String.valueOf(id));
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