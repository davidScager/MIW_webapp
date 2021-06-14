package com.example.cryptobank.service.mailSender;

import com.example.cryptobank.domain.maildata.MailData;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;

@Component
public class GenerateMailContent {
    private String mailContent;
    private Scanner fileReader;
    private final StringBuilder stringBuilder;

    public GenerateMailContent()  {
        this.stringBuilder = new StringBuilder();
    }

    public String setHtmlMail(MailData mailData) throws MalformedURLException, FileNotFoundException {
        this.fileReader = new Scanner(new File(mailData.getSecondUrl()));
        readHtmlFile();
        String correctText = mailContent.replace("TEKSTHIER", mailData.getMailText());
        String resetLink = CreateURLHelper.generateToken(mailData);
        return correctText.replace("URLHIER", resetLink);
    }

    private void readHtmlFile() {
        while (fileReader.hasNextLine()) {
            stringBuilder.append(fileReader.nextLine());
        }
        mailContent = String.valueOf(stringBuilder);
    }

        public String transactionOrderCancelledDueToBank(String username, String cryptocoin, double value) throws MalformedURLException {
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


