package com.example.cryptobank.domain.urls;

public class UrlAdresses {

    private final String address = "http://miw-team-2.nl";
    //private final String address = "http://localhost:8080";
    private final String registrationPage = address + "/register";
    private final String registrationPageUrl = address + "/registreren.html";
    private final String registrationFailedPage = address + "/register/failed";
    private final String registrationFailedPageUrl = address + "/registreren-mislukt.html";
    private final String registrationRequest = address + "/register/request";
    private final String RegistrationFinalized = address + "/register/finalize";

    private final String resetPassword = address + "/reset/resetpassword";
    private final String createNewPassword = address + "/reset/createnewpassword";
    private final String setNewPassword = address + "/reset/setnewpassword";
    private final String confirmed = address + "/confirmed.html";
    private final String getCreateNewPasswordPage = address + "/create_new_password.html";

    private final String loginPage = address + "/login.html";
    private final String login = address + "/login";
    private final String loginRedirect = address + "/login/redirect";

    private final String PortfolioReturnsPage = address + "/portfolioreturns";
    private final String TransactionHistory = address + "/transaction/transactionhistory";
    private final String transactionCost = address + "/transaction/transactioncost";
    private final String createTransaction = address + "/transaction/createtransaction";
    private final String myAssets = address + "/transaction/myassets";
    private final String bankAssets = address + "/transaction/bankassets";

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

    public String getCreateNewPassword() {
        return createNewPassword;
    }

    public String getSetNewPassword() {
        return setNewPassword;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getGetCreateNewPasswordPage() {
        return getCreateNewPasswordPage;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public String getLogin() {
        return login;
    }

    public String getLoginRedirect() {
        return loginRedirect;
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

    public String getMyAssets() {
        return myAssets;
    }

    public String getBankAssets() {
        return bankAssets;
    }
}
