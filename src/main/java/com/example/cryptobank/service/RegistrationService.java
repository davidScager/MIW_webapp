package com.example.cryptobank.service;

import com.example.cryptobank.domain.Actor;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.repository.RootRepository;
import com.example.cryptobank.security.HashAndSalt;
import com.example.cryptobank.security.SaltMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public String register(int bsn, String firstname, String infix, String surname,
                           String dateofbirth, String address, String email, String password, String username){
        User user = new User(bsn, firstname, infix, surname, dateofbirth, address, email, username);
        HashAndSalt hashAndSalt = hashService.hash(password);
        if (rootRepository.saveLogin(user, hashAndSalt)){

        }
        //Actor actor = new Actor();
        return "I see what you did there...";
    }

}
