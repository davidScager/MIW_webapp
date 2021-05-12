package com.example.cryptobank.repository;

import com.example.cryptobank.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UserDao {

    List<User> list();

    void create(User user);

    Optional<User> get(int bsn);

    void update(User user, int bsn);

    void delete(int bsn);
}