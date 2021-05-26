package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.repository.daointerfaces.LoginDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of loginAccount database access using MySQL and Spring JdbcTemplate
 * @author David_Scager
 */
@Repository
public class JdbcLoginDao implements LoginDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcActorDao.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcLoginDao(JdbcTemplate jdbcTemplate){
        logger.info("New JdbcLoginDao");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(String username, String hash) {
        jdbcTemplate.update(connection -> insertLoginStatement(username, hash, null, connection));
    }

    private PreparedStatement insertLoginStatement(String username, String hash, String token, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into loginaccount values (?, ?, ?)");
        ps.setString(1, username);
        ps.setString(2, hash);
        ps.setString(3, token);
        return ps;
    }

    public Optional<LoginAccount> get(String username){
        List<LoginAccount> loginList = jdbcTemplate.query(
                "select * from loginaccount where username = ?",
                (rs, rowNum) -> new LoginAccount(rs.getString("username"), rs.getString("password"), rs.getString("token")),
                username);
        if(loginList.size() != 1){
            return Optional.empty();
        } else {
            return Optional.of(loginList.get(0));
        }
    }

    @Override
    public void update(String username, String hash, String token) {
        jdbcTemplate.update("update loginaccount set username = ?, password  = ?, token = ? where username = ?",
                username, hash, token, username);
    }

    @Override
    public void delete(String username) {
        jdbcTemplate.update("delete from loginaccount where username = ?", username);
    }
}
