package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserLoginAccount;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.GenerateMailContext;
import com.example.cryptobank.service.mailSender.MailSenderService;
import com.example.cryptobank.service.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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
    private Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private final RootRepository rootRepository;
    private final TokenService tokenService;
    private final GenerateMailContext generateMailContext;
    private final MailSenderService mailSenderService;
    private final Map<String, UserLoginAccount> registrationCache;

    @Autowired
    public RegistrationService(RootRepository rootRepository, TokenService tokenService,
                               GenerateMailContext generateMailContext, MailSenderService mailSenderService) {
        this.rootRepository = rootRepository;
        this.tokenService = tokenService;
        this.generateMailContext = generateMailContext;
        this.mailSenderService = mailSenderService;
        this.registrationCache = new HashMap<>();
        logger.info("RegistrationProxy active");
    }

    public boolean validate(UserLoginAccount userLoginAccount){
        logger.info("Validating registration information with database");
        User user = userLoginAccount.getUser();
        return user != null
                && rootRepository.getLoginByUsername(userLoginAccount.getEmail()) == null
                && rootRepository.getUserByBsn(user.getBSN()) == null;
    }

    public String cacheNewUserWithToken(UserLoginAccount userLoginAccount){
        String token = tokenService.generateJwtToken(userLoginAccount.getEmail(), "Register", 30);
        registrationCache.put(token, userLoginAccount);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                registrationCache.remove(token);
                logger.info("Cache cleared");
            }
        }, 1800000);
        logger.info(registrationCache.get(token).toString());
        return token;
    }

    public void sendConfirmationEmail(String token, String email) {
        try {
            String mailText = generateMailContext.setRegistrationText(token);
            mailSenderService.sendMail(email, mailText, "Bevestig BitBank-registratie");
            logger.info("Registration confirmation email sent to new client");
        } catch (MalformedURLException | MessagingException urlMessageError) {
            urlMessageError.printStackTrace();
            logger.info("Failed to send email.");
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
            if (registrationCache.containsKey(token)){
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.info("Invalid token");
            registrationCache.remove(token);
            logger.info("Cache cleared");
            return false;
        }
    }

    //for old way of registration without confirmation email
    public User register(UserLoginAccount userLoginAccount, Role role){
        User user = userLoginAccount.getUser();
        rootRepository.registerLogin(user, userLoginAccount.getPassword());
        rootRepository.registerUser(user, role);
        return user;
    }
}
