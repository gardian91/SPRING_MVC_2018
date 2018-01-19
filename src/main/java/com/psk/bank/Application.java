package com.psk.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication(scanBasePackages = {"hello", "com.psk.bank"})
public class Application {

	
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
