package com.example.cryptobank.domain;

public class Actor {

    private final int userId;
    private String checkingaccount;
    private Role role;

    private Actor(int userId, String checkingaccount, Role role) {
        super();
        this.userId = userId;
        this.checkingaccount = checkingaccount;
        this.role = role;
    }

    private Actor(int userId, Role role) {
        this(userId, null, role);
    }

    public Actor(Role role) {
        this(0, role);
    }

    public int getUserId() {
        return userId;
    }

    public String getCheckingaccount() {
        return checkingaccount;
    }

    public void setCheckingaccount(String checkingaccount) {
        this.checkingaccount = checkingaccount;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
