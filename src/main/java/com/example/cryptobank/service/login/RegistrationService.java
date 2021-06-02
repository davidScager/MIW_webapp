package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserLoginAccount;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {
    User register(UserLoginAccount userLoginAccount, Role role);
    boolean validate(UserLoginAccount userLoginAccount);
}
