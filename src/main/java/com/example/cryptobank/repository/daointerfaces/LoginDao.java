package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.LoginAccount;

import java.util.Optional;

/**
 * Interface for Database Access of entity loginAccount
 * @author David_Scager
 */
public interface LoginDao {
    void create(String username, String hash);
    Optional<LoginAccount> get(String username);
    void update(String username, String hash, String token);
    void delete(String username);
}