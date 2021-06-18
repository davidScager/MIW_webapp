package com.example.cryptobank.service.mailSender;

import com.example.cryptobank.domain.maildata.MailData;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class GenerateMailContent {
    private String mailContent;
    private InputStream inputStream;
    private final StringBuilder stringBuilder;

    public GenerateMailContent()  {
        this.stringBuilder = new StringBuilder();
    }

    public String setHtmlMail(MailData mailData, String resetLink) {
            try {
                inputStream = getClass().getResourceAsStream ("/default_mail.html");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                mailContent = reader.lines()
                        .collect(Collectors.joining(System.lineSeparator()));
            } catch (Exception e) {
                e.printStackTrace();
            }
//        readHtmlFile();
            String correctText = mailContent.replace("TEKSTHIER", mailData.getMailText());
            return correctText.replace("URLHIER", resetLink);
        }
}


