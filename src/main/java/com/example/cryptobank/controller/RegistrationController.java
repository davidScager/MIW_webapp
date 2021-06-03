package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserLoginAccount;
import com.example.cryptobank.service.login.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        logger.info("New RegistrationController");
        this.registrationService = registrationService;
    }

    @PostMapping("/registrationrequest")
    public ResponseEntity<?> registrationRequestHandler(@RequestBody UserLoginAccount userLoginAccount){
        String message;
        if(registrationService.validate(userLoginAccount)){
            message = "Om registratie af te ronden is er een bevestigingslink verstuurd naar het opgegeven emailadres.";
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        }
        message = "Registratie mislukt.";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Register a new client
     * @return ResponseEntity<Object></Object>
     *
     * @author David_Scager
     */
    @PostMapping("/registerclient")
    public ResponseEntity<?> clientRegistrationHandler(@RequestParam("Authorization") String token){
        String subject = "Register";
        if (registrationService.validateToken(token, subject)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Register a new administrator
     * Request Json/application format
     * Return json parsed user object
     * @param userLoginAccount (UserLoginAccount) wrapper class for user and loginAccount objects
     * @return ResponseEntity<User></User>
     *
     * @author David_Scager
     */
    @PostMapping("/registeradmin")
    public ResponseEntity<?> adminRegistrationHandler(@RequestBody UserLoginAccount userLoginAccount){
        if (registrationService.validate(userLoginAccount)){
            User user = registrationService.register(userLoginAccount, Role.ADMINISTRATOR);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
