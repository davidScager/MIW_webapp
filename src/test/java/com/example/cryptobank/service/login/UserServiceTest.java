package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.login.LoginAccount;
import com.example.cryptobank.domain.user.FullName;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.user.UserAddress;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.security.HashService;
import com.example.cryptobank.service.security.TokenService;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;


import org.mockito.Mock;
import org.mockito.Mockito;

public class UserServiceTest {
    private UserService testUserService;
    private String username;
    private String password;
    private String hash;
    private String token;
    private LoginAccount loginAccount;
    private User user;
    private FullName fullName;
    private UserAddress userAddress;
    private int bsn;

    @Mock
    RootRepository rootRepository;

    @Mock
    HashService hashService;

    @Mock
    TokenService tokenService;

    @BeforeEach
    public void setupTest(){
        rootRepository = Mockito.mock(RootRepository.class);
        hashService = Mockito.mock(HashService.class);
        tokenService = Mockito.mock(TokenService.class);
        testUserService = new UserService(rootRepository, hashService, tokenService);
        username = "test_testman@hotmail.com";
        password = "test1234";
        hash = "123";
        token = "321";
        bsn = 12345;
        loginAccount = new LoginAccount(username, hash, token);
        fullName = new FullName("test", "", "man");
        userAddress = new UserAddress("1942nd", 73, "", "teststraat", "teststad");
        user = new User(bsn, fullName, "27-03-1996", userAddress, username);
    }

    @Test
    void mocksAvailable(){
        assert rootRepository != null;
        assert hashService != null;
        assert tokenService != null;
    }

    @Test
    void userGetsFound(){
        Mockito.when(rootRepository.getUserByUsername(username)).thenReturn(user);
        User testman = testUserService.getUser(username);
        assertThat(testman).isNotNull();
        assertThat(testman.getBSN()).isEqualTo(bsn);
    }

    @Test
    void userNotInDatabase(){
        Mockito.when(rootRepository.getUserByUsername(username)).thenReturn(null);
        User testman = testUserService.getUser(username);
        assertThat(testman).isNull();
    }

    @Test
    void userGetsVerified(){
        Mockito.when(rootRepository.getLoginByUsername(username)).thenReturn(loginAccount);
        Mockito.when(hashService.argon2idVerify(hash, password)).thenReturn(true);
        Mockito.when(rootRepository.getUserByUsername(username)).thenReturn(user);
        User testman = testUserService.verifyUser(username, password);
        assertThat(testman).isNotNull();
        assertThat(testman.getBSN()).isEqualTo(bsn);
    }

    @Test
    void userDoesNotExist(){
        Mockito.when(rootRepository.getLoginByUsername(username)).thenReturn(null);
        User testman = testUserService.verifyUser(username, password);
        assertThat(testman).isNull();
    }

    @Test
    void tokenReturnsUser(){
        Mockito.when(tokenService.parseToken(token, "session")).thenReturn(user.getEmail());
        Mockito.when(testUserService.getUser(username)).thenReturn(user);
        User testman = testUserService.getUserFromToken(token);
        assertThat(testman).isNotNull();
        assertThat(testman.getBSN()).isEqualTo(bsn);
    }

    @Test
    void tokenCheckFails(){
        Mockito.when(tokenService.parseToken(token, "session")).thenReturn(null);
        Mockito.when(testUserService.getUser(username)).thenReturn(user);
        User testman = testUserService.getUserFromToken(token);
        assertThat(testman).isNull();
    }

}
