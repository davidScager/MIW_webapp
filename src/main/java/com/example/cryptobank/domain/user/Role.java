package com.example.cryptobank.domain.user;

public enum Role {
    ADMINISTRATOR("administrator") ,
    BANK("bank"),
    CLIENT("client");

    private final String role;

   Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
   }
}
