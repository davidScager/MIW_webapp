package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.service.security.HashAndSalt;

import java.util.Optional;

/**
 * Interface for Database Access of entity loginAccount
 * @author David_Scager
 */
public interface LoginDao {
    void create(String username, HashAndSalt hashAndSalt);
    Optional<LoginAccount> get(String username);
    void update(String username, HashAndSalt hashAndSalt, String token);
    void delete(String username);
}
