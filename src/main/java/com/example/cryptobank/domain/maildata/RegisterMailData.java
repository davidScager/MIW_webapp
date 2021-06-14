package com.example.cryptobank.domain.maildata;

public class RegisterMailData extends MailData{
    private String url;
    private String sender;
    private String mailSubject;
    private String mailContent;
    private String receiverEmail;
    private String token;

    public RegisterMailData(String receiverEmail, String token) {
        this.url = "http://localhost:8080/register/finalize";
        this.sender = "BigBossNijntje@BitBank.com";
        this.mailSubject = "Registratie BitBank - Bevestigen";
        this.receiverEmail = receiverEmail;
        this.token = token;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
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
    public String getReceiverEmail() {
        return receiverEmail;
    }

    @Override
    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
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
}