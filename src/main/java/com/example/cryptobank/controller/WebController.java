package com.example.cryptobank.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(WebController.class);

    //@Autowired
    public WebController() {
        super();
        logger.info("New WebController");
    }

    //@GetMapping("/registertest")
    public String registrationPageHandler(){
        return "registreren";
    }

}
