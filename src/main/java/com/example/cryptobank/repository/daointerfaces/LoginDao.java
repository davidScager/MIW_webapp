package com.example.cryptobank.repository.daointerfaces;

import com.example.cryptobank.domain.login.LoginAccount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface for Database Access of entity loginAccount
 * @author David_Scager
 */
@Repository
public interface LoginDao {
    void create(String username, String password);
    Optional<LoginAccount> get(String username);
    void update(String username, String password, String token);
    void delete(String username);
}
