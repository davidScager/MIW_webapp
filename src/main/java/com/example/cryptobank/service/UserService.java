package com.example.cryptobank.service;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.repository.RootRepository;
import com.example.cryptobank.security.SaltMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private final RootRepository rootRepository;
    private final HashService hashService;

    @Autowired
    public UserService(RootRepository rootRepository, HashService hashService) {
        logger.info("New UserService");
        this.rootRepository = rootRepository;
        this.hashService = hashService;
    }

    public User verifyUser(String username, String password) {
        LoginAccount loginAccount = rootRepository.getLoginByUsername(username);
        if (loginAccount != null) {
            logger.info("LoginAccount contains " + loginAccount);
            String hash = loginAccount.getHash();
            if (hashService.argon2idVerify(hash, password)) {
                logger.info("Login verified");
                return rootRepository.getUserByUsername(username);
            }
        }
        logger.info("Login rejected");
        return null;
    }


}
