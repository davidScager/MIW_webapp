package com.example.cryptobank;

import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.service.currency.MethodRunOnScheduleHelper;
import com.example.cryptobank.service.login.RegistrationService;
import com.example.cryptobank.service.security.HashService;
import com.example.cryptobank.service.security.PepperService;
import com.example.cryptobank.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptoBankApplication implements CommandLineRunner {

    MethodRunOnScheduleHelper methodRunOnScheduleHelper;
    private final TransactionService transactionService;
    private final RegistrationService registrationService;

    @Autowired
    public CryptoBankApplication(MethodRunOnScheduleHelper methodRunOnScheduleHelper, TransactionService transactionService, RegistrationService registrationService) {
        this.methodRunOnScheduleHelper = methodRunOnScheduleHelper;
        this.transactionService = transactionService;
        this.registrationService = registrationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoBankApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		//testmain
        //methodRunOnScheduleHelper.getCurrencyDailyForHistoryValue();
        //methodRunOnScheduleHelper.getCurrencyDaily();

        // generate and register 3 users -David
        /*UserLoginAccount userLoginAccount1 = new UserLoginAccount(123456, "Johnny", "", "Bravo", "1997-11-01", "1234AB", 10, "bis", "dorpstraat", "Townsville", "johnny.bravo@cartoonnetwork.com", "ho-M0mm4");
        UserLoginAccount userLoginAccount2 = new UserLoginAccount(234567, "Mojo", "", "Jojo", "1997-11-01", "1234AB", 11, "", "EvilLairstreet", "Townsville", "mojo.jojo@badguy.com", "m0-j0-JoJo");
        UserLoginAccount userLoginAccount3 = new UserLoginAccount(345678, "Dexter", "", "Genius", "1997-11-01", "1234AB", 12, "bis", "SecretLabstreet", "Townsville", "boy.wonder@smartguy.com", "0mlet-du-From4ge");
        UserLoginAccount userLoginAccount4 = new UserLoginAccount(758274, "Testman", "", "Tester", "1997-11-01", "1234AB", 12, "bis", "SecretLabstreet", "Townsville", "testman.tester@hotmail.com", "TeStMaN1234");
        UserLoginAccount userLoginAccount5 = new UserLoginAccount(743292, "Testvrouw", "", "Tester", "1997-11-01", "1234AB", 12, "bis", "SecretLabstreet", "Townsville", "testvrouw.tester@hotmail.com", "TeStVrOuW1234");
        registrationService.register(userLoginAccount1, Role.CLIENT);
        registrationService.register(userLoginAccount2, Role.CLIENT);
        registrationService.register(userLoginAccount3, Role.CLIENT);
        registrationService.register(userLoginAccount4, Role.CLIENT);
        registrationService.register(userLoginAccount5, Role.CLIENT);*/
	}
}
