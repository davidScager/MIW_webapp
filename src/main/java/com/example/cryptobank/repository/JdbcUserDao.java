package com.example.cryptobank.repository;

import com.example.cryptobank.domain.User;
import com.example.cryptobank.repository.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class JdbcUserDao implements UserDao {

    private final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcUserDao");
    }

    RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setBSN(rs.getInt("bsn"));
        user.setId(rs.getLong("userid"));
        user.setFirstName(rs.getString("firstname"));
        user.setInfix(rs.getString("infix"));
        user.setSurname(rs.getString("surname"));
        user.setDateOfBirth(rs.getString("dateofbirth"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));

        return user;
    };
    @Override
    public List<User> list() {
        logger.debug("JdbcUserDao.list aangeroepen");
        String sql = "select * from user";
        return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public void create(User user) {
        logger.debug("JdbcUserDao.create aangeroepen voor user " + user.getBSN());
        String sql = "insert into user(bsn, userid, firstname, infix, surname, dateofbirth, address, email, username) values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getBSN(), user.getId(), user.getFirstName(), user.getInfix(), user.getSurname(), user.getDateOfBirth(), user.getAddress(), user.getEmail(), user.getUsername());
    }

    @Override
        public Optional<User> get(int bsn){
        logger.debug("JdbcUserDao.get aangeroepen voor user " + bsn);
        List<User> users = jdbcTemplate.query(
                    "select * from bitbankdb.user where bsn = ?",
                    rowMapper, bsn);
            if (users.size() != 1){
                return Optional.empty();
            } else {
                return Optional.of(users.get(0));
            }
        }

    @Override
    public void update(User user, int bsn) {
        logger.debug("JdbcUserDao.update aangeroepen voor user " + user.getBSN());
        String sql = "update user set bsn = ?, userid = ?, firstname = ?, infix = ?, surname = ?, dateofbirth = ?, address = ?, email = ?, username = ? where bsn = ?";
        jdbcTemplate.update(sql, user.getBSN(), user.getId(), user.getFirstName(), user.getInfix(), user.getSurname(), user.getDateOfBirth(), user.getAddress(), user.getEmail(), user.getUsername());
    }
    @Override
    public void delete(int bsn) {
        logger.debug("JdbcUserDao.delete aangeroepen voor user " + bsn);
        jdbcTemplate.update("delete from user where bsn = ?",bsn);
    }
}