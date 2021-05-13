package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Actor;
import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.service.RegistrationService;
import com.example.cryptobank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final UserService userService;
    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

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
     * @param email (String)
     * @param password (String)
     * @param username (String)
     * @return (String) Relay message
     *
     * @author David_Scager
     */
    @GetMapping("/registertest")
    public String registrationTestHandler(
            @RequestParam int bsn,
            @RequestParam String firstname,
            @RequestParam(required = false) String infix,
            @RequestParam String surname,
            @RequestParam String dateofbirth,
            @RequestParam String address,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String username){
        return registrationService.register(bsn, firstname, infix, surname, dateofbirth, address, email, password, username, Role.CLIENT);
    }

    @GetMapping("/registerClient")
    public User clientRegistrationHandler(
            @RequestParam int BSN,
            @RequestParam String firstName,
            @RequestParam(required = false) String infix,
            @RequestParam String surname,
            @RequestParam String dateOfBirth,
            @RequestParam String address,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String username) {
        User user = userService.register(BSN, firstName, infix, surname, dateOfBirth, address, email, username, Role.CLIENT);
        //loginAccountMaker.generateLogin(user, password);
        return user;
    }

    @GetMapping("/registerAdministrator")
    public User administratorRegistrationHandler(
            @RequestParam int BSN,
            @RequestParam String firstName,
            @RequestParam(required = false) String infix,
            @RequestParam String surname,
            @RequestParam String dateOfBirth,
            @RequestParam String address,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String username) {
        User user = userService.register(BSN, firstName, infix, surname, dateOfBirth, address, email, username, Role.ADMINISTRATOR);
        //loginAccountMaker.generateLogin(user, password);
        return user;
    }

}
