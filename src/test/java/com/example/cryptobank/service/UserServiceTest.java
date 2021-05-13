package com.example.cryptobank.service;

import com.example.cryptobank.controller.LoginController;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.repository.RootRepository;
import com.example.cryptobank.repository.UserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
/**
 @Auth HvS
 //todo work in progress
 **/

class UserServiceTest {

    @MockBean
    private static RootRepository mockRootRepository = Mockito.mock(RootRepository.class);

    public UserService userService = new UserService(mockRootRepository);

    @BeforeAll
    public static void setUp() {
        User huib = new User(13167, "huib", "van", "Straten", "29-01-1982", "van lierdreef", "huibvanstraten@gmail.com", "huib");
    }

    @Test
    void register() {
    }

    @Test
    void verifyUser() {
    }
}