package com.example.cryptobank.service.mailSender;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component
public class GenerateMailContext {
    private String resetURL;


    public GenerateMailContext() {
        this.resetURL = "http://localhost:8080/createnewpassword";
    }

    public String setResetText(String token) throws MalformedURLException {
        return "Dit is je url. klik er maar op, eikel. En niet meer vergeten! \n" + CreateURLHelper.generateToken(resetURL, token);
    }



}


