package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.maildata.MailData;
import com.example.cryptobank.domain.maildata.RegisterMailData;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.mailsenderfacade.MailSenderFacade;
import com.example.cryptobank.service.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Service for registering new users
 * @author David_Scager
 */
@Service
public class RegistrationService {
    private static final int DURATION_VALID = 30;
    private static final int REMOVE_TOKEN_TIMER = DURATION_VALID * 60000;
    private Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private final RootRepository rootRepository;
    private final TokenService tokenService;
    private final MailSenderFacade mailSenderFacade;
    private final Map<String, UserLoginAccount> registrationCache;

    @Autowired
    public RegistrationService(RootRepository rootRepository, TokenService tokenService,
                               @Qualifier("sendMailServiceFacade") MailSenderFacade mailSenderFacade) {
        this.rootRepository = rootRepository;
        this.tokenService = tokenService;
        this.mailSenderFacade = mailSenderFacade;
        this.registrationCache = new HashMap<>();
        logger.info("RegistrationService active");
    }

    public boolean validate(UserLoginAccount userLoginAccount){
        logger.info("Validating registration information with database");
        User user = userLoginAccount.getUser();
        //JdbcTemplate.queryForObject(select exists(dao)) voor de check
        //directly from userDao and loginDao
        return user != null
                && rootRepository.getLoginByUsername(userLoginAccount.getUser().getEmail()) == null
                && rootRepository.getUserByBsn(user.getBSN()) == null;
    }

    public String cacheNewUserWithToken(UserLoginAccount userLoginAccount){
        String token = tokenService.generateJwtToken(userLoginAccount.getUser().getEmail(), "Register", DURATION_VALID);
        registrationCache.put(token, userLoginAccount);
        logger.info("Registration Cached");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                registrationCache.remove(token);
                logger.info("Cache cleared");
            }
        }, REMOVE_TOKEN_TIMER);
        logger.info(registrationCache.get(token).toString());
        return token;
    }

    public void sendConfirmationEmail(String token, String email) {
        MailData registerMailData = new RegisterMailData(email, token);
        try {
            mailSenderFacade.sendMail(registerMailData);
            logger.info("Registration confirmation email sent to new client" + registerMailData);
        } catch (MalformedURLException | MessagingException | FileNotFoundException error) {
            logger.info("Failed to send email.");
            logger.error("URL or Messaging error caught." + error.getMessage());
        }
    }

    public void registerUser(String token){
        UserLoginAccount userLoginAccount = registrationCache.get(token) ;
        rootRepository.registerLogin(userLoginAccount.getUser(), userLoginAccount.getPassword());
        rootRepository.registerUser(userLoginAccount.getUser(), Role.CLIENT);
        registrationCache.remove(token);
        logger.info("Cache cleared");
        logger.info("New Client registered");
    }

    public boolean validateToken(String token, String subject) {
        try {
            tokenService.parseToken(token, subject);
            return registrationCache.containsKey(token);
        } catch (Exception e) {
            logger.info("Invalid token");
            registrationCache.remove(token);
            logger.info("Cache cleared");
            return false;
        }
    }

    //todo: verwijder voor einde project
    // momenteel alleen gebruikt in commandlineRunner om gebruikers voor uitproberen te registreren
    public User register(UserLoginAccount userLoginAccount, Role role){
        User user = userLoginAccount.getUser();
        rootRepository.registerLogin(userLoginAccount.getUser(), userLoginAccount.getPassword());
        rootRepository.registerUser(userLoginAccount.getUser(), Role.CLIENT);
        return user;
    }

}
