package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.user.Actor;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.repository.daointerfaces.ActorDao;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;
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
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;




@Repository
public class JdbcPortfolioDao implements PortfolioDao {

    private final JdbcTemplate jdbcTemplate;
    private ActorDao actorDao;

    @Autowired
    public JdbcPortfolioDao(JdbcTemplate jdbcTemplate, ActorDao actorDao) {
        super();
        this.actorDao = actorDao;
        this.jdbcTemplate = jdbcTemplate;
    }
    RowMapper<Portfolio> portfolioRowMapper = (resultSet, rownumber) -> {Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId(resultSet.getInt("portfolioId"));
        Actor actor = actorDao.get(resultSet.getInt("actor")).get();
        portfolio.setActor(actor);
        return portfolio;
    };

    //updated schema to auto_increment portfolioId and actor is now called actor in the database -David
    private PreparedStatement insertPortfolioStatement(Portfolio portfolio, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert INTO Portfolio (actor) values (?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, portfolio.getActor().getUserId());
        return preparedStatement;
    }


    @Override
    public List list() {
        return jdbcTemplate.query("Select * from portfolio", portfolioRowMapper);
    }

    @Override
    public void create(Portfolio portfolio) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertPortfolioStatement(portfolio, connection), keyHolder);
        int key = keyHolder.getKey().intValue();
        portfolio.setPortfolioId(key);
    }

    @Override
    public Optional<Portfolio> get(int id) throws MissingResourceException {
        List<Portfolio> portfolioList = jdbcTemplate.query("Select * from portfolio where portfolioId = ?", portfolioRowMapper,id);
        if (portfolioList.size() == 1) {
            return Optional.of(portfolioList.get(0));
        }
        throw new MissingResourceException("No such Portfolio", "Portfolio", String.valueOf(id));
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM portfolio WHERE PortfolioId = ?", id);
    }

    @Override
    public Portfolio getPortfolioIdByUserId(int userId) {
        String query = "SELECT * FROM portfolio WHERE actor = ?";
        Portfolio portfolio = jdbcTemplate.queryForObject( query, new Object[] { userId }, portfolioRowMapper);
        return portfolio;
    }

    @Override
    public int getUserIdByPortfolioId(int portfolioId){
        String sql ="Select * FROM portfolio WHERE PortfolioId = ?";
        Actor actor = jdbcTemplate.query(sql, portfolioRowMapper, portfolioId).get(0).getActor();
        return (int) actor.getUserId();
    }
}
