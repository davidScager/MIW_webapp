package com.example.cryptobank.repository;

import com.example.cryptobank.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UserDao {

    public List<User> list();

    public void create(User user);

    public Optional<User> get(int bsn);

    public void update(User user, int bsn);

    public void delete(int bsn);
}
