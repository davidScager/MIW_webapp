package com.example.cryptobank.domain.maildata;

public class RegisterMailData extends MailData{
    private String firstUrl;
    private String sender;
    private String mailSubject;
    private String mailText;
    private String mailContent;
    private String receiver;
    private String token;

    public RegisterMailData(String receiver, String token) {
        this.firstUrl = "http://localhost:8080/register/finalize";
        this.sender = "BigBossNijntje@BitBank.com";
        this.mailSubject = "Registratie BitBank - Email bevestiging";
        this.mailText = "Je bent er bijna! \n Klik a.u.b. binnen 30 minuten na ontvangst op de onderstaande knop om je registratie af te ronden.";
        this.receiver = receiver;
        this.token = token;
    }

    @Override
    public String getFirstUrl() {
        return null;
    }

    public void setFirstUrl(){
    }

    public String getSecondUrl() {
        return firstUrl;
    }

    public void setSecondUrl(String secondUrl){
        this.firstUrl = secondUrl;
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

    @Override
    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    @Override
    public String getMailText() {
        return mailText;
    }

    @Override
    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    @Override
    public String getMailContent() {
        return mailContent;
    }

    @Override
    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    @Override
    public String getReceiver() {
        return receiver;
    }

    @Override
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}