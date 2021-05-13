package com.example.cryptobank.repository;

import com.example.cryptobank.domain.User;
import com.example.cryptobank.security.HashAndSalt;
import org.springframework.stereotype.Repository;

/**
 * Interface for Database Access of entity loginAccount
 * @author David_Scager
 */
public interface LoginDao {
    boolean isRegistered(User user);
    void create(User user, HashAndSalt hashAndSalt);
    HashAndSalt login(User user, String password);
}
