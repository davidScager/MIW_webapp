package com.example.cryptobank.service.mailSender;

import com.example.cryptobank.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailSenderService {
    private EmailConfig emailConfiguration;
    private User user; //TODO HvS: als ik client wil toevoegen in constructor, dan wil Spring iets... uitzoeken

    @Autowired
    public MailSenderService(EmailConfig emailConfiguration, User user) {
        this.emailConfiguration = emailConfiguration;
        this.user = user;
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
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("bla");
        mailMessage.setText("BlaBla");

        mailSender.send(mailMessage);
    }
}

