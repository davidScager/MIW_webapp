package com.example.cryptobank.service;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.repository.RootRepository;
import com.example.cryptobank.security.HashAndSalt;
import com.example.cryptobank.security.SaltMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * @param bsn (int)
     * @param firstname (String)
     * @param infix (String)
     * @param surname (String)
     * @param dateofbirth (String)
     * @param address (String)
     * @param email (String)
     * @param password (String)
     * @param role (Role)
     * @return (String) message for client
     */
    public String register(int bsn, String firstname, String infix, String surname, String dateofbirth, String address, String email, String password, Role role){
        if (rootRepository.getLoginByUsername(email) == null && rootRepository.getUserByBsn(bsn) == null){
            User user = new User(bsn, firstname, infix, surname, dateofbirth, address, email);
            HashAndSalt hashAndSalt = hashService.argon2idHash(password);
            rootRepository.registerLogin(user, hashAndSalt);
            rootRepository.registerUser(user, role);
            return "New user registered. You can now login to your new account.";
        } else {
            return "You are already registered.";
        }
    }

}
