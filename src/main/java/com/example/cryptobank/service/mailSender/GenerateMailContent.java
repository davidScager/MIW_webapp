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

    public String setHtmlMail(MailData mailData, String resetLink) throws FileNotFoundException {
        this.fileReader = new Scanner(new File(mailData.getPageUrl()));
        readHtmlFile();
        String correctText = mailContent.replace("TEKSTHIER", mailData.getMailText());
        return correctText.replace("URLHIER", resetLink);
    }

    private void readHtmlFile() {
        while (fileReader.hasNextLine()) {
            stringBuilder.append(fileReader.nextLine());
        }
        mailContent = String.valueOf(stringBuilder);
    }
}


