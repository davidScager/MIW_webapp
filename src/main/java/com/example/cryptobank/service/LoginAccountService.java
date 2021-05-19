package com.example.cryptobank.service;

import com.example.cryptobank.repository.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAccountService {
    private Logger logger = LoggerFactory.getLogger(LoginAccountService.class);
    private final RootRepository rootRepository;
    private final TokenService tokenService;

    @Autowired
    public LoginAccountService(RootRepository rootRepository, TokenService tokenService) {
        this.tokenService = tokenService;
        logger.info("new LoginAccountService");
        this.rootRepository = rootRepository;
    }

    public boolean verifyAccount(String email) {
        return rootRepository.getLoginByUsername(email) != null;
    }

    public String addTokenToLoginAccount(String username) {
        String token = tokenService.generateJwtToken("reset", 10);
        //rootRepository.storeResetToken(username, token);
        return token;
    }


}
