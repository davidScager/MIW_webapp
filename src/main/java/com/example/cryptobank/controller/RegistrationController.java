package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Actor;
import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserLoginAccount;
import com.example.cryptobank.security.SaltMaker;
import com.example.cryptobank.service.RegistrationService;
import com.example.cryptobank.service.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    private Logger logger = LoggerFactory.getLogger(SaltMaker.class);
    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        logger.info("New RegistrationController");
        this.registrationService = registrationService;
    }
/*
    /**
     * For initial test purposes
     * takes arguments from Postman request
     * inserts all information into the right tables
     * and relays message to client.
     * @param bsn (int)
     * @param firstname (String)
     * @param infix (String)
     * @param surname (String)
     * @param dateofbirth (String)
     * @param address (String)
     * @param email (String) also used as username
     * @param password (String)
     * @return (String) Relay message
     *
     * @author David_Scager
     */
/*
    @GetMapping("/registerClient")
    public String registrationTestHandler(
            @RequestParam int bsn,
            @RequestParam String firstname,
            @RequestParam(required = false) String infix,
            @RequestParam String surname,
            @RequestParam String dateofbirth,
            @RequestParam String address,
            @RequestParam String email,
            @RequestParam String password){
        if (registrationService.validate()) {
            return registrationService.register(bsn, firstname, infix, surname, dateofbirth, address, email, password, Role.CLIENT);
        }
    }
*/

    @PostMapping("/registerclient")
    public ResponseEntity<User> registrationTestHandler(@RequestBody UserLoginAccount userLoginAccount){
        if (registrationService.validate(userLoginAccount)) {
            User user = registrationService.register(userLoginAccount, Role.CLIENT);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


/*
    @GetMapping("/registerAdministrator")
    public String administratorRegistrationHandler(
            @RequestParam int bsn,
            @RequestParam String firstname,
            @RequestParam(required = false) String infix,
            @RequestParam String surname,
            @RequestParam String dateofbirth,
            @RequestParam String address,
            @RequestParam String email,
            @RequestParam String password) {
        return registrationService.register(bsn, firstname, infix, surname, dateofbirth, address, email, password, Role.ADMINISTRATOR);
    }
*/

}
