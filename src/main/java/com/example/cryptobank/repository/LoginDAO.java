package com.example.cryptobank.repository;

import com.example.cryptobank.domain.User;
import com.example.cryptobank.security.HashAndSalt;
import org.springframework.stereotype.Repository;

public interface LoginDAO {
    boolean isRegistered(User user);
    boolean register(User user, HashAndSalt hashAndSalt);
    boolean login(User user, String password);
}
