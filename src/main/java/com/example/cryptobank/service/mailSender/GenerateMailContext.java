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


    }public String transactionOrderCancelledDueToBank(String username, String cryptocoin, double value) throws MalformedURLException {
        return String.format("Geachte %s,\nU heeft een order geplaatst voor de koop/verkoop van %s bij het bereiken van de waarde %.2f." +
                        "Helaas heeft de bank niet de voldoende hoeveelheid assets om uw order te voltooien. Onze welgemeende excuses"
                , username, cryptocoin, value);
    }

    public String transactionOrderCancelledDueToClient(String username, String cryptocoin, double value) throws MalformedURLException {
        return String.format("Geachte %s,\nU heeft een order geplaatst voor de koop/verkoop van %s bij het bereiken van de waarde %.2f." +
                        "Helaas heeft u niet de voldoende hoeveelheid assets om uw order te voltooien. Wij zullen deze daarom annuleren"
                , username, cryptocoin, value);
    }

    public String transactionOrderConfirmed(String username, String cryptocoin, double value) throws MalformedURLException {
        return String.format("Geachte %s,\nU heeft een order geplaatst voor de koop/verkoop van %s bij het bereiken van de waarde %.2f." +
                        "Wij kunnen u mededelen dat de transactie is geslaagd."
                , username, cryptocoin, value);
    }

    public String transactionOrderInDollars(String username, String cryptocoin, double value) throws MalformedURLException {
        return String.format("Geachte %s,\nU heeft een order geplaatst voor de verkoop van %s bij het bereiken van de waarde %.2f." +
                        "Helaas hebben wij op het moment van transactie niet voldoende van de gewenste assets. Wij betalen u uit in dollars" +
                        "Indien u het hier mee oneens bent kunt u via deze link de transactie terugdraaien //harder, better...."
                , username, cryptocoin, value);
    }



}


