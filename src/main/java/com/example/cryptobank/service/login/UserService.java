package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.security.HashService;
import com.example.cryptobank.service.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private final RootRepository rootRepository;
    private final HashService hashService;
    private final TokenService tokenService;

    @Autowired
    public UserService(RootRepository rootRepository, HashService hashService, TokenService tokenService) {
        this.tokenService = tokenService;
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

    public User getUser(String username){
        return rootRepository.getUserByUsername(username);
    }

    public User getUserFromToken(String token){
        return getUser(tokenService.parseToken(token, "session"));
    }

}
