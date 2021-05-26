package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.Actor;
import com.example.cryptobank.domain.Role;
import com.example.cryptobank.repository.daointerfaces.ActorDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Optional;

@Repository
public class JdbcActorDao implements ActorDao {

    private final Logger logger = LoggerFactory.getLogger(JdbcActorDao.class);

    private JdbcTemplate jdbcTemplate;

    public JdbcActorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcActorDao");
    }

    RowMapper<Actor> rowMapper = (rs, rowNum) -> {
        Actor actor = new Actor();
        actor.setUserId(rs.getLong("userid"));
        actor.setCheckingaccount(rs.getString("checkingaccount"));
        switch (rs.getString("role")) {
            case "administrator" -> actor.setRole(Role.ADMINISTRATOR);
            case "bank" -> actor.setRole(Role.BANK);
            case "client" -> actor.setRole(Role.CLIENT);
        }
        return actor;
    };

    @Override
    public List<Actor> list() {
        String sql = "select * from actor";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Actor actor) {
        logger.debug("JdbcActorDao.create aangeroepen");
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertActorStatement(actor, connection), keyholder);
        long newKey = keyholder.getKey().longValue();
        actor.setUserId(newKey);
        logger.debug("De userId van de actor is " + actor.getUserId());
    }

    //added checkingAccount insert -David
    private PreparedStatement insertActorStatement(Actor actor, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into actor (checkingAccount, role) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, actor.getCheckingaccount());
        ps.setString(2, actor.getRole().toString());
        return ps;
    }

    @Override
    public Optional<Actor> get(long userId) {
        logger.debug("JdbcActorDao.get aangeroepen voor actor " + userId);
        List<Actor> actors = jdbcTemplate.query(
                "select * from bitbankdb.actor where userId = ?",
                rowMapper, userId);
        if (actors.size() != 1) {
            return Optional.empty();
        } else {
            System.out.println(actors.get(0));
            return Optional.of(actors.get(0));
        }
    }

    @Override
    public void update(Actor actor, long userId) {
        logger.debug("JdbcActorDao.update aangeroepen voor actor " + actor.getUserId());
        String sql = "update actor set userId = ?, checkingAccount = ?, role = ? where userId = ?";
        jdbcTemplate.update(sql, userId, actor.getCheckingaccount(), actor.getRole().toString(), actor.getUserId());
    }

    @Override
    public void delete(long userId) {
        logger.debug("JdbcActorDao.delete aangeroepen voor actor " + userId);
        jdbcTemplate.update("delete from actor where userid = ?", userId);
    }
}
