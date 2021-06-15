package com.example.cryptobank.domain.maildata;

public class ResetMailData extends MailData {
    private String linkUrl;
    private String pageUrl;
    private String sender;
    private String mailSubject;
    private String mailText;
    private String mailContent;
    private String receiver;
    private String token;

    public ResetMailData(String receiver, String token) {
        this.linkUrl = "http://localhost:8080/create_new_password.html";
        this.pageUrl = "src/main/resources/static/default_mail.html";
        this.sender = "BigBossNijntje@BitBank.com";
        this.mailSubject = "Reset uw account";
        this.receiver = receiver;
        this.token = token;
        this.mailText = "U heeft aangegeven uw wachtwoord te willen veranderen. Indien dit klopt, druk dan op de onderstaande knop.";
    }

    @Override
    public String getLinkUrl() {
        return linkUrl;
    }

    @Override
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
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
    public String toString() {
        return "AssetMailData{" +
                "linkUrl='" + linkUrl + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", sender='" + sender + '\'' +
                ", mailSubject='" + mailSubject + '\'' +
                ", mailText='" + mailText + '\'' +
                ", mailContent='" + mailContent + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
