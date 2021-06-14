package com.example.cryptobank.domain.maildata;

public abstract class MailData {
    private String url;
    private String sender;
    private String mailSubject;
    private String mailContent;
    private String ReceiverEmail;
    private String token;

    public abstract String getUrl();

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getReceiverEmail() {
        return ReceiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        ReceiverEmail = receiverEmail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
}
