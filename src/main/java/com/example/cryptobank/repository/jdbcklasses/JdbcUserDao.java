package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.FullName;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserAddress;
import com.example.cryptobank.repository.daointerfaces.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        user.setAddress(new UserAddress(rs.getString("streetName"), rs.getInt("houseNr"), rs.getString("addition"),
                rs.getString("postalCode"), rs.getString("residence")));
        user.setEmail(rs.getString("email"));

        return user;
    };

    @Override
    public List<User> list() {
        logger.debug("JdbcUserDao.list aangeroepen");
        String sql = "select * from bitbankdb.user";
        return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public void create(User user) {
        logger.debug("JdbcUserDao.create aangeroepen voor user " + user.getBSN());
        String sql = "insert into bitbankdb.user(bsn, userid, firstname, infix, surname, dateofbirth, streetName, houseNr, addition, postalCode, residence, email) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getBSN(), user.getId(), user.getFullName().getFirstName(), user.getFullName().getInfix(), user.getFullName().getSurname(),
                user.getDateOfBirth(), user.getAddress().getStreetName(), user.getAddress().getHouseNr(), user.getAddress().getAddition(), user.getAddress().getPostalCode(), user.getAddress().getResidence(), user.getEmail());
    }

    @Override
    public User get(String username){
        logger.debug("JdbcUserDao.get aangeroepen voor user " + username);
        List<User> users = jdbcTemplate.query(
                "select * from bitbankdb.user where email = ?",
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
        String sql = "update bitbankdb.user set bsn = ?, userid = ?, firstname = ?, infix = ?, surname = ?, dateofbirth = ?, " +
                "streetName = ? , houseNr = ? , addition = ? , postalCode = ?, residence = ?, email = ? where bsn = ?";
        jdbcTemplate.update(sql, user.getBSN(), user.getId(),
                user.getFullName().getFirstName(), user.getFullName().getInfix(), user.getFullName().getSurname(),
                user.getDateOfBirth(),
                user.getAddress().getStreetName(), user.getAddress().getHouseNr(), user.getAddress().getAddition(), user.getAddress().getPostalCode(), user.getAddress().getResidence(),
                user.getEmail(), bsn);
    }

    @Override
    public void delete(int bsn) {
        logger.debug("JdbcUserDao.delete aangeroepen voor user " + bsn);
        jdbcTemplate.update("delete from bitbankdb.user where bsn = ?",bsn);
    }
}