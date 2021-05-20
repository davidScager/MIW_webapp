package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Component //voor mailserver. moet nog naar gekeken worden
public class User {
    private final Logger logger = LoggerFactory.getLogger(User.class);
    private int BSN;
    private long id;
    private String firstName;
    private String infix;
    private String surname;
    private String dateOfBirth;
    private String address;

    @Email
    @NotNull
    private String email;

    public User(int BSN, String firstName, String infix, String surname, String dateOfBirth, String address, String email) {
        this.BSN = BSN;
        this.firstName = firstName;
        this.infix = infix;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.id = 0;
        logger.info("New User");
    }

    @Override
    public String toString() {
        return "User{" +
                "BSN=" + BSN +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", infix='" + infix + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
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

}
