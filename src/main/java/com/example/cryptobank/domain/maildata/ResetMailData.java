package com.example.cryptobank.domain.maildata;

public class ResetMailData extends MailData {
    private String url;
    private String sender;
    private String mailSubject;
    private String mailContent;
    private String ReceiverEmail;
    private String token;


    public ResetMailData(String receiverEmail, String token) {
        this.url = "http://localhost:8080/create_new_password.html";
        this.sender = "BigBossNijntje@BitBank.com";
        this.mailSubject = "Reset uw account";
        ReceiverEmail = receiverEmail;
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

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    @Override
    public String getReceiverEmail() {
        return ReceiverEmail;
    }

    @Override
    public void setReceiverEmail(String receiverEmail) {
        ReceiverEmail = receiverEmail;
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
}
