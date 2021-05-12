package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Actor;
import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
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
