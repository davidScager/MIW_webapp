package com.example.cryptobank.service.login;

import org.springframework.stereotype.Service;

@Service
public interface LoginAccountService {

    boolean verifyAccount(String email);
    String addTokenToLoginAccount(String username);
    void updateResetPassword(String username, String password);
    boolean isTokenStored(String username);

}
