package com.example.cryptobank.service.mailSender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.junit.jupiter.api.Assertions.*;

class MailSenderServiceTest {

    private static JavaMailSenderImpl mockMail = Mockito.mock(JavaMailSenderImpl.class);

    @MockBean
    private EmailConfig emailConfig;


    @Autowired
    public MailSenderServiceTest(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }
    public MailSenderService mailSenderService = new MailSenderService(emailConfig);

    @BeforeEach
    void setUp() {
    }

    @Test
    void sendMail() {
    }
}