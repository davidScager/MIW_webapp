package com.example.cryptobank.repository;

import com.example.cryptobank.domain.User;
import com.example.cryptobank.security.HashAndSalt;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLoginDAO implements LoginDAO{

    @Override
    public boolean isRegistered(User user) {
        return false;
    }

    @Override
    public boolean register(User user, HashAndSalt hashAndSalt) {
        return false;
    }

    @Override
    public boolean login(User user, String password) {
        return false;
    }
}
