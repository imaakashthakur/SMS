package com.manjitmentor.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class, args);
		printBanner();
	}

	public static void printBanner(){
		log.info("\n\n,d88~~\\      e    e      ,d88~~\\ \n" +
				"8888        d8b  d8b     8888    \n" +
				"`Y88b      d888bdY88b    `Y88b   \n" +
				" `Y88b,   / Y88Y Y888b    `Y88b, \n" +
				"   8888  /   YY   Y888b     8888 \n" +
				"\\__88P' /          Y888b \\__88P' \n" +
				"                                 ");
	}

}
