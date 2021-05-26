package com.example.cryptobank.service;

import com.example.cryptobank.domain.FullName;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserAddress;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 @Auth HvS
 //todo work in progress
 **/

class UserServiceTest {

    @MockBean
    private static RootRepository mockRootRepository = Mockito.mock(RootRepository.class);

//    public UserService userService = new UserService(mockRootRepository, pepperService);

    @BeforeAll
    public static void setUp() {
        User huib = new User(13167, new FullName("huib", "van", "Straten"), "29-01-1982", new UserAddress("van lierdreef", 10, "a", "1234ab", "Blaricum"), "huibvanstraten@gmail.com");
    }

    @Test
    void register() {
    }

    @Test
    void verifyUser() {
    }
}