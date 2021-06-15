package com.example.cryptobank.domain.maildata;

public class AssetMailData extends MailData {
    private String linkUrl;
    private String pageUrl;
    private String sender;
    private String mailSubject;
    private String mailText;
    private String mailContent;
    private String receiver;

    public AssetMailData() {
        this.linkUrl = "http://localhost:8080/logincontroller.html";
        this.pageUrl = "src/main/resources/static/default_mail.html";
        this.sender = "BigBossNijntje@BitBank.com";
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
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

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
