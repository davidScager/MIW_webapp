package com.example.cryptobank.controller;

import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.service.login.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView viewHtmlRegisterHandler(){
        return new RedirectView("http://localhost:8080/registreren.html");
    }

    @GetMapping("/failed")
    public RedirectView viewHtmlRegisterFailedHandler(){
        return new RedirectView("http://localhost:8080/registreren-mislukt.html");
    }


    @PostMapping("/request")
    public ResponseEntity<?> registrationRequestHandler(@RequestBody UserLoginAccount userLoginAccount){
        logger.info(userLoginAccount.toString());
        if(registrationService.validate(userLoginAccount)){
            logger.info("Registratie gevalideerd");
            String token = registrationService.cacheNewUserWithToken(userLoginAccount);
//            registrationService.sendConfirmationEmail(token, userLoginAccount.getUser().getEmail());
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
    @GetMapping("/finalize")
    public RedirectView clientRegistrationHandler(@RequestParam("Authorization") String token){
        String subject = "Register";
        if (registrationService.validateToken(token, subject)){
            registrationService.registerUser(token);
            return new RedirectView("http://localhost:8080/loginredirect");
        }
        return new RedirectView("http://localhost:8080/register/failed");
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
