package com.example.cryptobank.service.mailSender;

import com.example.cryptobank.model.Client;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mailSenderService {
    private EmailConfiguration emailConfiguration;
    private Client client; //TODO HvS: als ik client wil toevoegen in constructor, dan wil Spring iets... uitzoeken

    public mailSenderService(EmailConfiguration emailConfiguration) {
        this.emailConfiguration = emailConfiguration;
    }

    public void sendMail() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfiguration.getHost());
        mailSender.setPort(this.emailConfiguration.getPort());
        mailSender.setUsername(this.emailConfiguration.getUsername());
        mailSender.setPassword(this.emailConfiguration.getPassword());

        //here we can compose email (or if we need multiple, we can make the mail extract method
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("Baas@BitBank.com");
        mailMessage.setTo(client.getEmail());
        mailMessage.setSubject("bla");
        mailMessage.setText("BlaBla");

        mailSender.send(mailMessage);
    }
}

