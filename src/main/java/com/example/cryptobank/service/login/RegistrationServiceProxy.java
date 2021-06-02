package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserLoginAccount;
import org.hibernate.annotations.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Proxy
public class RegistrationServiceProxy implements RegistrationService{
    private final Logger logger = LoggerFactory.getLogger(RegistrationServiceClass.class);
    private final RegistrationService registrationService;
    //add assertions and such before registering the client
    //Could also use this to temporarily store registration tokens

    @Autowired
    public RegistrationServiceProxy(RegistrationService registrationService) {
        this.registrationService = registrationService;
        logger.info("RegistrationProxy active");
    }

    @Override
    public User register(UserLoginAccount userLoginAccount, Role role) {
        return registrationService.register(userLoginAccount, role);
    }

    @Override
    public boolean validate(UserLoginAccount userLoginAccount) {

        return registrationService.validate(userLoginAccount);
    }
}
