package com.example.cryptobank;

import com.example.cryptobank.model.Client;
import com.example.cryptobank.service.mailSender.EmailConfig;
import com.example.cryptobank.service.mailSender.MailSenderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailSender;

@SpringBootApplication
public class CryptoBankApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CryptoBankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//testmain

	}
}
