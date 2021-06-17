package com.example.cryptobank.service.mailSender;

import com.example.cryptobank.domain.maildata.MailData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class GenerateMailContent {
    private String mailContent;
    private Scanner fileReader;
    private final StringBuilder stringBuilder;

    public GenerateMailContent()  {
        this.stringBuilder = new StringBuilder();
    }

    public String setHtmlMail(MailData mailData, String resetLink) throws IOException {
        InputStream resource = new ClassPathResource("/default_mail.html").getInputStream();
        try ( BufferedReader reader = new BufferedReader( new InputStreamReader(resource)) ) {
            String mailHtml = reader.lines().collect(Collectors.joining("\n"));
            this.fileReader = new Scanner(new File(mailHtml));
        } catch (IOException exception) {
            System.out.println("nope");
        }
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


