package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.security.HashService;
import com.example.cryptobank.service.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAccountServiceClass implements LoginAccountService{
    private Logger logger = LoggerFactory.getLogger(LoginAccountServiceClass.class);
    private final RootRepository rootRepository;
    private final TokenService tokenService;
    private final HashService hashService;

    @Autowired
    public LoginAccountServiceClass(RootRepository rootRepository, TokenService tokenService, HashService hashService) {
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
        String hash = hashService.argon2idHash(password);
        rootRepository.updateLoginAccount(username, hash, null);
    }

    public boolean isTokenStored(String username) {
        LoginAccount loginAccount = rootRepository.getLoginAccount(username).orElse(null);
        logger.info(loginAccount.getToken());
        return loginAccount.getToken() != null;
    }
}
