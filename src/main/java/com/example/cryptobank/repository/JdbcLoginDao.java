package com.example.cryptobank.repository;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.security.HashAndSalt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of loginAccount database access using MySQL and Spring JdbcTemplate
 * @author David_Scager
 */
@Repository
public class JdbcLoginDao implements LoginDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcActorDao.class);
    private JdbcTemplate jdbcTemplate;

    public JdbcLoginDao(JdbcTemplate jdbcTemplate){
        logger.info("New JdbcLoginDao");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isRegistered(User user) {
        List<LoginAccount> registeredUser = jdbcTemplate.query(
                "select * from bitbankdb.loginaccount where username = ?",
                (rs, rowNum) -> new LoginAccount(rs.getString("username"), rs.getString("hash"), rs.getString("salt")),
                user.getUsername());
        return !registeredUser.isEmpty();
    }

    @Override
    public void create(User user, HashAndSalt hashAndSalt) {
        jdbcTemplate.update(connection -> insertLoginStatement(user, hashAndSalt, connection));
    }

    private PreparedStatement insertLoginStatement(User user, HashAndSalt hashAndSalt, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into bitbankdb.loginaccount values (?, ?, ?)");
        ps.setString(1, user.getUsername());
        ps.setString(2, hashAndSalt.getHash());
        ps.setString(3, hashAndSalt.getSalt());
        return ps;
    }

    @Override
    public HashAndSalt login(User user, String password) {
        return new HashAndSalt("hash", "salt");
    }
}
