package com.example.cryptobank.service.mailSender;

import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;


//@SpringBootTest
//class MailSenderServiceTest {
//
//    private MailSenderService mailSenderService;
//
//    @Autowired
//    public MailSenderServiceTest(MailSenderService mailSenderService){
//        super();
//        this.mailSenderService = mailSenderService;
//    }
//
//    @Test
//    public void testServiceAvailable() {
//        assertThat(mailSenderService).isNotNull();
//    }
//
//    @Test
//    public void testService() throws MessagingException {
//        String email = "";
//        String mailText = "";
//        String mailSubject = "";
//        String actual = mailSenderService.sendMail(email, mailText, mailSubject);
//        String expected = ("");
//        assertEquals(expected, actual);
//    }
//}

class MailSenderServiceTest {

//    private static JavaMailSenderImpl mockMail = Mockito.mock(JavaMailSenderImpl.class);

    private EmailConfig emailConfig;
    private MailSenderService mailSenderService = new MailSenderService(emailConfig);

    @Autowired
    public MailSenderServiceTest(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }


    @Test
    public void sendMail() {
//        String actual = mailSenderService.sendMail("annasophie@koppelaar.com", "Hallo, hoe gaat het?", "Hoi");
//        String expected = "";
//        assertThat(actual).isEqualTo(expected);
    }

}
