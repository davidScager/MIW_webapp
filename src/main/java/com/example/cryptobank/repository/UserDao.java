package com.example.cryptobank.repository;

import com.example.cryptobank.domain.User;
import com.example.cryptobank.repository.Dao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
@Component
public class UserDao implements Dao<User> {

    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        String sql = "select * from bitbankdb.user";
        return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public void create(User user) {
        String sql = "insert into bitbankdb.user(bsn, userid, firstname, infix, surname, dateofbirth, address, email, username) values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getBSN(), user.getId(), user.getFirstName(), user.getInfix(), user.getSurname(), user.getDateOfBirth(), user.getAddress(), user.getEmail(), user.getUsername());
    }

    @Override
        public Optional<User> get(int bsn){
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
        String sql = "update bitbankdb.user set bsn = ?, userid = ?, firstname = ?, infix = ?, surname = ?, dateofbirth = ?, address = ?, email = ?, username = ? where bsn = ?";
        jdbcTemplate.update(sql, user.getBSN(), user.getId(), user.getFirstName(), user.getInfix(), user.getSurname(), user.getDateOfBirth(), user.getAddress(), user.getEmail(), user.getUsername());
    }

    @Override
    public void delete(int bsn) {
        jdbcTemplate.update("delete from bitbankdb.user where bsn = ?",bsn);
    }
}