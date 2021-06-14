package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.login.LoginAccount;
import com.example.cryptobank.repository.daointerfaces.LoginDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Implementation of loginAccount database access using MySQL and Spring JdbcTemplate
 * @author David Scager
 */
@Repository
public class JdbcLoginDao implements LoginDao {
    //move logger.info() to here
    private final Logger logger = LoggerFactory.getLogger(JdbcActorDao.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcLoginDao(JdbcTemplate jdbcTemplate){
        logger.info("New JdbcLoginDao");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(String username, String password) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into loginaccount values (?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, null);
            return ps;
        });
    }

    public LoginAccount get(String username){
        List<LoginAccount> loginList = jdbcTemplate.query(
                "select * from loginaccount where username = ?",
                (rs, rowNum) -> new LoginAccount(rs.getString("username"), rs.getString("password"), rs.getString("token")),
                username);
        if(loginList.isEmpty()){
            return null;
        } else {
            return loginList.get(0);
        }
    }

    @Override
    public void update(String username, String password, String token) {
        jdbcTemplate.update("update loginaccount set username = ?, password  = ?, token = ? where username = ?",
                username, password, token, username);
    }

    @Override
    public void delete(String username) {
        jdbcTemplate.update("delete from loginaccount where username = ?", username);
    }
}
