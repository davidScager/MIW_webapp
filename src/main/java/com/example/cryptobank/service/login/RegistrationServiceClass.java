package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserLoginAccount;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.security.HashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service for registering new users
 * @author David_Scager
 */
@Component
public class RegistrationServiceClass implements RegistrationService {
    private Logger logger = LoggerFactory.getLogger(RegistrationServiceClass.class);
    private final RootRepository rootRepository;
    private final HashService hashService;

    @Autowired
    public RegistrationServiceClass(RootRepository rootRepository, HashService hashService){
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
    @Override
    public User register(UserLoginAccount userLoginAccount, Role role, String token){
        User user = userLoginAccount.getUser() ;
        rootRepository.registerLogin(user, hashService.argon2idHash(userLoginAccount.getPassword()));
        rootRepository.registerUser(user, role);
        logger.info("New Client registered");
        return user;
    }

    @Override
    public boolean validate(UserLoginAccount userLoginAccount){
        logger.info("Validating registration information with database");
        User user = userLoginAccount.getUser() ;
        return rootRepository.getLoginByUsername(userLoginAccount.getEmail()) == null && rootRepository.getUserByBsn(user.getBSN()) == null;
    }

    @Override
    public boolean validateToken(String token, String subject) {
        return false;
    }
}
