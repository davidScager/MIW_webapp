package com.example.cryptobank.service;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.repository.RootRepository;
import com.example.cryptobank.security.HashAndSalt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAccountService {
    private Logger logger = LoggerFactory.getLogger(LoginAccountService.class);
    private final RootRepository rootRepository;
    private final TokenService tokenService;
    private final HashService hashService;

    @Autowired
    public LoginAccountService(RootRepository rootRepository, TokenService tokenService, HashService hashService) {
        logger.info("new LoginAccountService");
        this.tokenService = tokenService;
        this.hashService = hashService;
        this.rootRepository = rootRepository;
    }

    public boolean verifyAccount(String email) {
        return rootRepository.getLoginByUsername(email) != null;
    }

    public String addTokenToLoginAccount(String username) {
        String token = tokenService.generateJwtToken(username, "reset", 10);
        rootRepository.storeResetToken(username, token);
        return token;
    }

    public void updateResetPassword(String username, String password) {
        HashAndSalt hashAndSalt = hashService.argon2idHash(password);
        rootRepository.updateLoginAccount(username, hashAndSalt, null);
    }

    public boolean isTokenStored(String username) {
        LoginAccount loginAccount = rootRepository.getLoginAccount(username).orElse(null);
        logger.info(loginAccount.getToken());
        return loginAccount.getToken() != null;
    }
}
