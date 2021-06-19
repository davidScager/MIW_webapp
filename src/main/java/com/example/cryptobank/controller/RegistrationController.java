package com.example.cryptobank.controller;

import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.domain.urls.UrlAdresses;
import com.example.cryptobank.service.login.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Api endpoints dealing with registration of new clients
 *
 * @author David_Scager, Reyndert Mehrer, HvS
 */
@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final RegistrationService registrationService;
    private final UrlAdresses urlAdresses = new UrlAdresses();

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        logger.info("New RegistrationController");
        this.registrationService = registrationService;
    }

    /**
     * Redirects to registration page template
     *
     * @return (RedirectView)
     */
    @GetMapping
    public RedirectView viewHtmlRegisterHandler(){
        return new RedirectView(urlAdresses.getRegistrationPageUrl());
    }

    /**
     * Redirects to registration failed template
     *
     * @return (RedirectView)
     */
    @GetMapping("/failed")
    public RedirectView viewHtmlRegisterFailedHandler(){
        return new RedirectView(urlAdresses.getRegistrationFailedPageUrl());
    }

    /**
     * Validate registration request with backend
     * Uses services to cache user information with Jwt, sends Jwt to user via email
     * and returns status 200 ok
     * Or if not valid returns status 400 bad request
     *
     * @param userLoginAccount (UserLoginAccount)
     * @return (ResponseEntity<?></?>)
     */
    @PostMapping("/request")
    public ResponseEntity<?> registrationRequestHandler(@RequestBody UserLoginAccount userLoginAccount){
        logger.info(userLoginAccount.toString());
        userLoginAccount.addRequiredData();
        if(registrationService.validate(userLoginAccount) && userLoginAccount.allRequiredData()){
            logger.info("Registratie gevalideerd");
            String token = registrationService.cacheNewUserWithToken(userLoginAccount);
            if(registrationService.confirmationEmailSent(token, userLoginAccount.getUser().getEmail())){
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        logger.info("Registratie geweigerd");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Register a new client in database when provided with valid Jwt
     * and redirect to login page
     * Or redirect to registration failed page
     *
     * @return ResponseEntity<Object></Object>
     */
    @GetMapping("/finalize")
    public RedirectView clientRegistrationHandler(@RequestParam("Authorization") String token){
        String subject = "Register";
        if (registrationService.validateToken(token, subject)){
            registrationService.registerUser(token);
            return new RedirectView(urlAdresses.getLoginPage());
        }
        return new RedirectView(urlAdresses.getRegistrationFailedPageUrl());
    }

}
