package com.example.cryptobank;

import com.example.cryptobank.service.currency.MethodRunOnScheduleHelper;
import com.example.cryptobank.service.transaction.TransactionService;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptoBankApplication implements CommandLineRunner {

    MethodRunOnScheduleHelper methodRunOnScheduleHelper;
    private final TransactionService transactionService;

    @Autowired
    public CryptoBankApplication(MethodRunOnScheduleHelper methodRunOnScheduleHelper, TransactionService transactionService) {
        this.methodRunOnScheduleHelper = methodRunOnScheduleHelper;
        this.transactionService = transactionService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoBankApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		//testmain
        setHistory();
        setEveryminute();
	}

	@Order(1)
	public void setHistory(){
        methodRunOnScheduleHelper.getCurrencyDailyForHistoryValue();
    }

    @Order(2)
    public void setEveryminute(){
        methodRunOnScheduleHelper.getCurrencyDaily();
    }
}
