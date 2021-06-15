package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.user.FullName;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.user.UserAddress;
import com.example.cryptobank.repository.daointerfaces.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        user.setFullName(new FullName(rs.getString("firstname"), rs.getString("infix"), rs.getString("surname")));
        user.setDateOfBirth(rs.getString("dateofbirth"));
        user.setUserAddress(new UserAddress(rs.getString("streetName"), rs.getInt("houseNr"), rs.getString("addition"),
                rs.getString("postalCode"), rs.getString("residence")));
        user.setEmail(rs.getString("email"));

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
        String sql = "insert into user(bsn, userid, firstname, infix, surname, dateofbirth, streetName, houseNr, addition, postalCode, residence, email) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getBSN(), user.getId(), user.getFullName().getFirstName(), user.getFullName().getInfix(), user.getFullName().getSurname(),
                user.getDateOfBirth(), user.getUserAddress().getStreetName(), user.getUserAddress().getHouseNr(), user.getUserAddress().getAddition(), user.getUserAddress().getPostalCode(), user.getUserAddress().getResidence(), user.getEmail());
    }

    @Override
    public User get(String username){
        logger.debug("JdbcUserDao.get aangeroepen voor user " + username);
        List<User> users = jdbcTemplate.query(
                "select * from user where email = ?",
                rowMapper, username);
        if (users.size() != 1){
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public void update(User user, int bsn) {
        logger.debug("JdbcUserDao.update aangeroepen voor user " + user.getBSN());
        String sql = "update user set bsn = ?, userid = ?, firstname = ?, infix = ?, surname = ?, dateofbirth = ?, " +
                "streetName = ? , houseNr = ? , addition = ? , postalCode = ?, residence = ?, email = ? where bsn = ?";
        jdbcTemplate.update(sql, user.getBSN(), user.getId(),
                user.getFullName().getFirstName(), user.getFullName().getInfix(), user.getFullName().getSurname(),
                user.getDateOfBirth(),
                user.getUserAddress().getStreetName(), user.getUserAddress().getHouseNr(), user.getUserAddress().getAddition(), user.getUserAddress().getPostalCode(), user.getUserAddress().getResidence(),
                user.getEmail(), bsn);
    }

    @Override
    public void delete(int bsn) {
        logger.debug("JdbcUserDao.delete aangeroepen voor user " + bsn);
        jdbcTemplate.update("delete from user where bsn = ?",bsn);
    }

    @Override
    public boolean userExists(String username, int bsn){
        String sql = "select exists(select * from user where email = '" + username + "' and bsn = '" + bsn + "');";
        try {
            return jdbcTemplate.queryForObject(sql, Boolean.class);
        } catch (EmptyResultDataAccessException error) {
            logger.info(error.getMessage());
            return false;
        }
    }
}