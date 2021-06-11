package com.example.cryptobank.repository;

import com.example.cryptobank.domain.user.Actor;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.repository.daointerfaces.ActorDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ActorDaoTest {

    private ActorDao daoUnderTest;

    @Autowired
    public ActorDaoTest(@Qualifier("actorDao") ActorDao daoUnderTest) {
        this.daoUnderTest = daoUnderTest;
    }

    @Test
    void list() {
    }

    @Test
    void createActorTest() {
        Actor actor = new Actor(Role.CLIENT);
        daoUnderTest.create(actor);
        long actorId = actor.getUserId();
        Actor actor2 = daoUnderTest.get(actorId).orElseThrow();
        assertThat(actor).isEqualTo(actor2);
    }

    @Test
    void get() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}




