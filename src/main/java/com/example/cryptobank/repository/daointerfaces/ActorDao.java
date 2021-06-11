package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.user.Actor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorDao {

    public List<Actor> list();

    public void create(Actor actor);

    public Optional<Actor> get(long userId);

    public void update(Actor actor, long userId);

    public void delete(long userId);
}
