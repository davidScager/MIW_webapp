package com.example.cryptobank.service.mailSender;

import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @MockBean
    private static RootRepository mockRootRepository = Mockito.mock(RootRepository.class);
//    private MailSenderService mailSenderService = new MailSenderService(mockRootRepository);

    @BeforeAll
    public static void setUp() {

    }


}


