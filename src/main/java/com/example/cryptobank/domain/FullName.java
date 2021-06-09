package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class FullName {
    private final Logger logger = LoggerFactory.getLogger(FullName.class);
    private String firstName;
    private String infix;
    private String surname;

    public FullName(){
    }

    public FullName(String firstName, String infix, String surname) {
        this();
        logger.info("New FullName");
        this.firstName = firstName;
        this.infix = infix;
        this.surname = surname;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstName).append(" ");
        if(infix != null){
            stringBuilder.append(infix).append(" ");
        }
        stringBuilder.append(surname);
        return stringBuilder.toString();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getInfix() {
        return infix;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FullName)) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(getFirstName(), fullName.getFirstName()) && Objects.equals(getInfix(), fullName.getInfix()) && Objects.equals(getSurname(), fullName.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getInfix(), getSurname());
    }
}
