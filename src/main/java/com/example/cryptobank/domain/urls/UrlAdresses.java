package com.example.cryptobank.domain.urls;

public class UrlAdresses {

    //private final String address = "http://miw-team-2.nl";
    private final String address = "http://localhost:8080";

    private final String homeschermPage = address + "/homescherm.html";
    private final String homeschermingelogdPage = address + "/homeschermingelogd.html";
    private final String contactPageUrl = address + "/Contact.html";

    private final String registrationPage = address + "/register";
    private final String registrationPageUrl = address + "/registreren.html";
    private final String registrationFailedPage = address + "/register/failed";
    private final String registrationFailedPageUrl = address + "/registreren_mislukt.html";

    private final String registrationRequest = address + "/register/request";
    private final String RegistrationFinalized = address + "/register/finalize";

    private final String resetPassword = address + "/reset/reset_password";
    private final String resetPasswordPage = address + "/reset_password.html";
    private final String resetConfirmedPage = address + "/confirmed.html";
    private final String resetConfirmed = address + "/reset/confirmed";
    private final String resetDenied = address + "/reset/denied";

    private final String resetDeniedPage = address + "/resetdenied.html";
    private final String createNewPassword = address + "/reset/createnewpassword";
    private final String newPassword = address + "/reset/setnewpassword";
    private final String createNewPasswordPage = address + "/create_new_password.html";

    private final String loginPage = address + "/login.html";
    private final String login = address + "/login";

    private final String portfolioPage = address + "/portfolio";
    private final String portfolioPageUrl = address + "/portfolio.html";
    private final String PortfolioReturnsPage = address + "/portfolio/returns";
    private final String PortfolioReturnsPageUrl = address + "/PortfolioReturns.html";
    private final String transactionPage = address + "/Transaction.html";

    public String getContactPageUrl() {
        return contactPageUrl;
    }

    public String getPortfolioPage() {
        return portfolioPage;
    }

    public String getPortfolioPageUrl() {
        return portfolioPageUrl;
    }

    public String getPortfolioReturnsPageUrl() {
        return PortfolioReturnsPageUrl;
    }

    private final String TransactionHistory = address + "/transaction/history";
    private final String TransactionHistoryPage = address + "/transaction/history";
    private final String TransactionHistoryPageUrl = address + "/TransactionHistory.html";
    private final String transactionCost = address + "/transaction/transactioncost";
    private final String createTransaction = address + "/transaction/createtransaction";
    private final String myassets = address + "/transaction/myassets";
    private final String bankassets = address + "/transaction/bankassets";

    public String getHomeschermPage() {
        return homeschermPage;
    }

    public String getHomeschermingelogdPage() {
        return homeschermingelogdPage;
    }

    public String getTransactionHistoryPage() {
        return TransactionHistoryPage;
    }

    public String getTransactionHistoryPageUrl() {
        return TransactionHistoryPageUrl;
    }

    public String getTransactionPage() {
        return transactionPage;
    }

    public String getAddress() {
        return address;
    }

    public String getRegistrationPage() {
        return registrationPage;
    }

    public String getRegistrationPageUrl() {
        return registrationPageUrl;
    }

    public String getRegistrationFailedPage() {
        return registrationFailedPage;
    }

    public String getRegistrationFailedPageUrl() {
        return registrationFailedPageUrl;
    }

    public String getRegistrationRequest() {
        return registrationRequest;
    }

    public String getRegistrationFinalized() {
        return RegistrationFinalized;
    }

    public String getResetPassword() {
        return resetPassword;
    }

    public String getResetPasswordPage() {
        return resetPasswordPage;
    }

    public String getCreateNewPassword() {
        return createNewPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getCreateNewPasswordPage() {
        return createNewPasswordPage;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public String getLogin() {
        return login;
    }

    public String getPortfolioReturnsPage() {
        return PortfolioReturnsPage;
    }

    public String getTransactionHistory() {
        return TransactionHistory;
    }

    public String getTransactionCost() {
        return transactionCost;
    }

    public String getCreateTransaction() {
        return createTransaction;
    }

    public String getMyassets() {
        return myassets;
    }

    public String getBankassets() {
        return bankassets;
    }

    public String getResetConfirmedPage() {
        return resetConfirmedPage;
    }

    public String getResetDeniedPage() {
        return resetDeniedPage;
    }

    public String getResetConfirmed() {
        return resetConfirmed;
    }

    public String getResetDenied() {
        return resetDenied;
    }
}

