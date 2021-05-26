package com.example.cryptobank.service;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserLoginAccount;
import com.example.cryptobank.repository.RootRepository;
import com.example.cryptobank.security.HashAndSalt;
import com.example.cryptobank.security.SaltMaker;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service for registering new users
 * @author David_Scager
 */
@Service
public class RegistrationService {
    private Logger logger = LoggerFactory.getLogger(SaltMaker.class);
    private RootRepository rootRepository;
    private HashService hashService;

    public RegistrationService(RootRepository rootRepository, HashService hashService){
        super();
        logger.info("New RegistrationService");
        this.rootRepository = rootRepository;
        this.hashService = hashService;
    }

    /**
     * Check that user is not yet registered,
     * register user, hash password and store login account
     * Relay result message back to client
     * @param userLoginAccount (UserLoginAccount)
     * @param role (Role)
     * @return (String) message for client
     */
    public User register(UserLoginAccount userLoginAccount, Role role){
        User user = userLoginAccount.getUser();
        if (rootRepository.getLoginByUsername(userLoginAccount.getEmail()) == null && rootRepository.getUserByBsn(user.getBSN()) == null){
            HashAndSalt hashAndSalt = hashService.argon2idHash(userLoginAccount.getPassword());
            rootRepository.registerLogin(user, hashAndSalt);
            rootRepository.registerUser(user, role);
            return user;
        } else {
            return null;
        }
    }

    public boolean validate(UserLoginAccount userLoginAccount){
        return true;
    }

}
