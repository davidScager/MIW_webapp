package com.example.cryptobank.repository;

import com.example.cryptobank.domain.user.*;
import com.example.cryptobank.repository.daointerfaces.ActorDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ActorDaoTest {

//    @MockBean
//    private MethodRunOnScheduleHelper methodRunOnScheduleHelper;
//
//    @MockBean
//    private TransactionService transactionService;
//
//    @MockBean
//    private AssetController assetController;

    private Logger logger = LoggerFactory.getLogger(ActorDaoTest.class);
    private final ActorDao actorDao;
    private final JdbcTemplate jdbcTemplate;
    private static Actor expectedActor;


    @Autowired
    public ActorDaoTest(ActorDao actorDao, JdbcTemplate jdbcTemplate) {
        super();
        this.actorDao = actorDao;
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New ActorDaoTest Started");
    }

    @BeforeAll
    public static void setup() {
        expectedActor = new Actor(Role.CLIENT);
        expectedActor.setCheckingAccount("12345");
    }

    @Test
    @Order(1)
    public void actorDaoIsAvailable() {
        assertThat(actorDao).isNotNull();
    }

    @Test
    @Order(2)
    void createActorTest() {
        actorDao.create(expectedActor);
        long idActor = expectedActor.getUserId();
        expectedActor.setUserId(idActor);
        String sql = "select exists(select * from actor where userId=" + idActor + ")";
        boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class);
        assertThat(exists).isTrue();
    }

    @Test
    @Order(3)
    void getActorTest() {
        Optional<Actor> optionalActor = actorDao.get(expectedActor.getUserId());
        Actor actor = optionalActor.orElse(null);
        assertThat(actor).isNotNull();
    }

    @Test
    @Order(4)
    void list() {
        List<Actor> actorList = actorDao.list();
        int expectedAmountOfActors = 7;
        int actorsInActorList = actorList.size();
        assertThat(actorsInActorList).isEqualTo(expectedAmountOfActors);
        actorList.forEach(actor -> assertThat(actor).isInstanceOf(Actor.class));
    }

    @Test
    @Order(5)
    void update() {
        long actorId = expectedActor.getUserId();
        String checkingAccount = expectedActor.getCheckingAccount();
        expectedActor.setCheckingAccount("54321");
        actorDao.update(expectedActor, actorId);
        Optional<Actor> updatedOptionalActor = actorDao.get(actorId);
        Actor updatedActor = updatedOptionalActor.orElse(null);
        assert updatedActor != null;
        String updatedCheckingAccount = updatedActor.getCheckingAccount();
        assertThat(checkingAccount).isEqualTo("12345");
        assertThat(updatedCheckingAccount).isEqualTo("54321");
        assertThat(updatedCheckingAccount).isNotEqualTo(checkingAccount);
    }

    @Test
    @Order(6)
    void delete() {
        String sql = "select count(*) from actor";
        Optional<Actor> optionalActor = actorDao.get(1);
        Actor actor = optionalActor.orElse(null);
        assert actor != null;
        long userId = actor.getUserId();
        int numberOfRows = jdbcTemplate.queryForObject(sql, Integer.class);
        actorDao.delete(userId);
        int numberOfRowsAfterDelete = jdbcTemplate.queryForObject(sql, Integer.class);
        assertThat(numberOfRowsAfterDelete).isLessThan(numberOfRows);
    }
}