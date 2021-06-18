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
    private FileReader fileReader;
    private final StringBuilder stringBuilder;

    public GenerateMailContent()  {
        this.stringBuilder = new StringBuilder();
    }

    public String setHtmlMail(MailData mailData, String resetLink) {
        try {
            fileReader = new FileReader("src/main/resources/static/default_mail.html");
            BufferedReader reader = new BufferedReader(fileReader);
            mailContent = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        readHtmlFile();
        String correctText = mailContent.replace("TEKSTHIER", mailData.getMailText());
        return correctText.replace("URLHIER", resetLink);
    }

//        private void readHtmlFile() {
//        while (fileReader.hasNextLine()) {
//            stringBuilder.append(fileReader.nextLine());
//        }
//        mailContent = String.valueOf(stringBuilder);
//    }
}


