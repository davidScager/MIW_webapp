package com.example.cryptobank.domain;

public class FullName {
    private String firstName;
    private String infix;
    private String surname;

    public FullName(String firstName, String infix, String surname) {
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

    public String getFirstName() {
        return firstName;
    }

    public String getInfix() {
        return infix;
    }

    public String getSurname() {
        return surname;
    }
}