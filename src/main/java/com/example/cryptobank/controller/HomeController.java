package com.example.cryptobank.controller;

import com.example.cryptobank.domain.urls.UrlAdresses;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.service.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping()
public class HomeController {

    private UrlAdresses urlAdresses = new UrlAdresses();
    private UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public RedirectView showResetPage() {
        return new RedirectView(urlAdresses.getHomeschermPage());
    }

    @GetMapping("/contact")
    public RedirectView showContactPage() {
        return new RedirectView(urlAdresses.getContactPageUrl());
    }

    @GetMapping("/homeschermingelogd")
    public RedirectView showHomeSchermIngelogdPage() {
        return new RedirectView(urlAdresses.getHomeschermingelogdPage());
    }

    @PostMapping("/checkuser")
    public User checkUser(@RequestHeader(value = "Authorization") String token) {
        return userService.getUserFromToken(token);
    }

}

