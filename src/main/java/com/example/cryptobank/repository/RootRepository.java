package com.example.cryptobank.repository;

import com.example.cryptobank.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class RootRepository {

    private final UserDao userDao;

    public RootRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    public void saveUser(User user){
        userDao.create(user);
    }
}
