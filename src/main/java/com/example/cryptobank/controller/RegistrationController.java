package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserLoginAccount;
import com.example.cryptobank.service.login.RegistrationService;
import com.example.cryptobank.service.login.RegistrationServiceClass;
import org.hibernate.annotations.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private RegistrationService registrationService;
    private RegistrationServiceClass registrationServiceClass;

    @Autowired
    public RegistrationController(RegistrationService registrationService, RegistrationServiceClass registrationServiceClass) {
        logger.info("New RegistrationController");
        this.registrationService = registrationService;
        this.registrationServiceClass = registrationServiceClass;
    }

/*    @GetMapping
    public ResponseEntity<?> viewHtmlHandler(){
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(registreren.html)
    }*/

    @PostMapping("/request")
    public ResponseEntity<?> registrationRequestHandler(@RequestBody UserLoginAccount userLoginAccount){
        if(registrationService.validate(userLoginAccount)){
            String message = "Om registratie af te ronden is er een bevestigingslink naar het opgegeven emailadres verstuurd.";
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

    /**
     * Register a new client
     * @return ResponseEntity<Object></Object>
     *
     * @author David_Scager
     */
    @PostMapping("/finalize")
    public ResponseEntity<?> clientRegistrationHandler(@RequestParam("Authorization") String token){
        String subject = "Register";
        if (registrationService.validateToken(token, subject)){
            UserLoginAccount userLoginAccount = null;
            User user = registrationService.register(userLoginAccount, Role.CLIENT, token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/client")
    public ResponseEntity<?> tempClientRegistrationHandler(@RequestBody UserLoginAccount userLoginAccount){
        if (registrationServiceClass.validate(userLoginAccount)){
            String token = "token";
            User user = registrationServiceClass.register(userLoginAccount, Role.CLIENT, token);
            return new ResponseEntity<>(user, HttpStatus.OK);
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
            String token = "token";
            User user = registrationService.register(userLoginAccount, Role.ADMINISTRATOR, token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
