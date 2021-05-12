package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

// for now only made to match to mailsender

@Component
public class User {

    private final Logger logger = LoggerFactory.getLogger(User.class);

    private int BSN;
    private long id;
    private String firstName;
    private String infix;
    private String surname;
    private String dateOfBirth;
    private String address;
    private String username;

    @Email
    @NotNull
    private String email;

    public User(int BSN, String firstName, String infix, String surname, String dateOfBirth, String address, String email, String username) {
        this.BSN = BSN;
        this.firstName = firstName;
        this.infix = infix;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.username = username;
        this.id = 0;
        logger.info("New User");
    }

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBSN() {
        return BSN;
    }

    public void setBSN(int BSN) {
        this.BSN = BSN;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
