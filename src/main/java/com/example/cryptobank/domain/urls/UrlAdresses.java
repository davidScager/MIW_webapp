package com.example.cryptobank.domain.urls;

public class UrlAdresses {

    private final String address = "http://miw-team-2.nl";
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

    private final String login = address + "/logincontroller.html";
    private final String loginRedirect = address + "/loginredirect";

    private final String PortfolioReturnsPage = address + "/portfolioreturns";
    private final String TransactionHistory = address + "/transaction/transactionhistory";
    private final String transactionCost = address + "/transaction/transactioncost";
    private final String createTransaction = address + "/transaction/createtransaction";
    private final String myassets = address + "/transaction/myassets";
    private final String bankassets = address + "/transaction/bankassets";
}
