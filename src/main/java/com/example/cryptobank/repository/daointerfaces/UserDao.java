package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserDao {

    List<User> list();

    void create(User user);

    User get(String username);

    void update(User user, int bsn);

    void delete(int bsn);
}