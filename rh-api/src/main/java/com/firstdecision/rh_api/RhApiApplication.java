package com.firstdecision.rh_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RhApiApplication {

	@RequestMapping("/")
	public String home() {
		return "Hello, this is the RH API!";
	}

	public static void main(String[] args) {
		SpringApplication.run(RhApiApplication.class, args);
	}

}
