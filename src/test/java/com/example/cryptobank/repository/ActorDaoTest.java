package com.example.cryptobank.repository;

import com.example.cryptobank.controller.AssetController;
import com.example.cryptobank.domain.user.Actor;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.repository.daointerfaces.ActorDao;
import com.example.cryptobank.repository.daointerfaces.UserDao;
import com.example.cryptobank.service.currency.MethodRunOnScheduleHelper;
import com.example.cryptobank.service.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @Autowired
    public ActorDaoTest(ActorDao actorDao) {
        super();
        this.actorDao = actorDao;
        logger.info("New ActorDaoTest Started");
    }

    @Test
    public void actorDaoIsAvailable() {
        assertThat(actorDao).isNotNull();
    }

    @Test
    void getActorTest() {
        Optional<Actor> optionalActor = actorDao.get(7);
        Actor actor = optionalActor.orElse(null);
        assertThat(actor).isNotNull();
    }

    @Test
    void createActorTest() {
        Actor actor = new Actor(Role.CLIENT);
        actorDao.create(actor);
        long idActor = actor.getUserId();
        Actor actor2 = actorDao.get(idActor).orElseThrow();
        long idActor2 = actor2.getUserId();
        assertThat(idActor).isEqualTo(idActor2);
    }

    @Test
    void list() {
        List<Actor> actorList = actorDao.list();
        int expectedAmountOfActors = 7;
        int actorsInActorList = actorList.size();
        assertThat(actorsInActorList).isEqualTo(expectedAmountOfActors);
        actorList.forEach(actor -> assertThat(actor).isInstanceOf(Actor.class));
    }

    @Test
    void update() {
        Optional<Actor> optionalActor = actorDao.get(7);
        Actor actor = optionalActor.orElse(null);
        assert actor != null;
        long actorId = actor.getUserId();
        String checkingAccount = actor.getCheckingAccount();
        actor.setCheckingAccount("1234321");
        actorDao.update(actor, actorId);
        Optional<Actor> updatedOptionalActor = actorDao.get(7);
        Actor updatedActor = optionalActor.orElse(null);
        String updatedCheckingAccount = updatedActor.getCheckingAccount();
        assertThat(updatedCheckingAccount).isNotEqualTo(checkingAccount);
    }

    @Test
    void delete() {
        List<Actor> actorList = actorDao.list();
        Actor actor = actorList.get(0);
        long userId = actor.getUserId();
        actorDao.delete(userId);
        List<Actor> actorListAfterDelete = actorDao.list();
        assertThat(actor).isNotIn(actorListAfterDelete);
    }
}