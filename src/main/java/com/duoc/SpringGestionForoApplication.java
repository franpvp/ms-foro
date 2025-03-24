package com.duoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringGestionForoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGestionForoApplication.class, args);
	}

}
