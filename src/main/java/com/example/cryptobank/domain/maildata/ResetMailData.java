package com.example.cryptobank.domain.maildata;

public class ResetMailData extends MailData {
    private String firstUrl;
    private String secondUrl;
    private String sender;
    private String mailSubject;
    private String mailText;
    private String mailContent;
    private String receiver;
    private String token;

    public ResetMailData(String receiver, String token) {
        this.firstUrl = "http://localhost:8080/create_new_password.html";
        this.secondUrl = "src/main/resources/static/resetPasswordMail.html";
        this.sender = "BigBossNijntje@BitBank.com";
        this.mailSubject = "Reset uw account";
        this.receiver = receiver;
        this.token = token;
        this.mailText = "U heeft aangegeven uw wachtwoord te willen veranderen. Indien dit klopt, druk dan op de onderstaande knop.";
    }

    @Override
    public String getFirstUrl() {
        return firstUrl;
    }

    @Override
    public void setFirstUrl(String firstUrl) {
        this.firstUrl = firstUrl;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    @Override
    public String getReceiver() {
        return receiver;
    }

    @Override
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getMailContent() {
        return mailContent;
    }

    @Override
    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getSecondUrl() {
        return secondUrl;
    }

    public void setSecondUrl(String secondUrl) {
        this.secondUrl = secondUrl;
    }

    @Override
    public String getMailText() {
        return mailText;
    }

    @Override
    public void setMailText(String mailText) {
        this.mailText = mailText;
    }
}
