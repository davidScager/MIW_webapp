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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/register")
public class RegistrationController {
    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        logger.info("New RegistrationController");
        this.registrationService = registrationService;
    }


    @GetMapping
    public String viewHtmlHandler(){
        return "<html>" +
                "<script>window.location.replace('http://localhost:8080/registreren.html')</script>" +
                "</html>";
    }


    @PostMapping("/request")
    public ResponseEntity<?> registrationRequestHandler(@RequestBody UserLoginAccount userLoginAccount){
        if(registrationService.validate(userLoginAccount)){
            logger.info("Registratie gevalideerd");
            String token = registrationService.cacheNewUserWithToken(userLoginAccount);
            registrationService.sendConfirmationEmail(token, userLoginAccount.getEmail());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        logger.info("Registratie geweigerd");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Register a new client
     * @return ResponseEntity<Object></Object>
     *
     * @author David_Scager
     */
    @PostMapping("/finalize")
    public ResponseEntity<?> clientRegistrationHandler(@RequestHeader("Authorization") String token){
        String subject = "Register";
        if (registrationService.validateToken(token, subject)){
            registrationService.registerUser(token);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/client")
    public ResponseEntity<?> tempClientRegistrationHandler(@RequestBody UserLoginAccount userLoginAccount){
        if (registrationService.validate(userLoginAccount)){
            User user = registrationService.register(userLoginAccount, Role.CLIENT);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
