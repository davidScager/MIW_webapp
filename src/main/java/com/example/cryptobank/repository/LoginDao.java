package com.example.cryptobank.repository;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.security.HashAndSalt;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface for Database Access of entity loginAccount
 * @author David_Scager
 */
public interface LoginDao {
    void create(String username, HashAndSalt hashAndSalt);
    Optional<LoginAccount> get(String username);
    void update(String username, HashAndSalt hashAndSalt);
    void delete(String username);
}
