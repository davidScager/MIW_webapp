package com.example.cryptobank.controller;

import com.example.cryptobank.domain.urls.UrlAdresses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping()
public class HomeController {

    private UrlAdresses urlAdresses = new UrlAdresses();

    @GetMapping
    public RedirectView showResetPage() {
        return new RedirectView(urlAdresses.getHomeschermPage());
    }

    @GetMapping("/contact")
    public RedirectView showContactPage() {
        return new RedirectView(urlAdresses.getContactPageUrl());
    }

}
