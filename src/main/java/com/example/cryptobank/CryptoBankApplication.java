package com.example.cryptobank;

import com.example.cryptobank.service.MethodRunOnScheduleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptoBankApplication implements CommandLineRunner {

    MethodRunOnScheduleHelper methodRunOnScheduleHelper;

    @Autowired
    public CryptoBankApplication(MethodRunOnScheduleHelper methodRunOnScheduleHelper) {
        this.methodRunOnScheduleHelper = methodRunOnScheduleHelper;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoBankApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		//testmain
        /*methodRunOnScheduleHelper.getCurrencyDailyForHistoryValue();*/
	}
}
