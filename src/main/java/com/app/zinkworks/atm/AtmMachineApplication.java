package com.app.zinkworks.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AtmMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmMachineApplication.class, args);
	}


	@Bean
	public RestTemplate prepareRestTemplate() {
		return new RestTemplate();
	}

}
