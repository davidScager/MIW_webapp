package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.login.LoginAccount;
import org.springframework.stereotype.Repository;

/**
 * Interface for Database Access of entity loginAccount
 * @author David_Scager
 */
public interface LoginDao {
    void create(String username, String password);
    LoginAccount get(String username);
    void update(String username, String password, String token);
    void delete(String username);
    boolean loginExists(String username);
}
