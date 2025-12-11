package com.GamingGear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
	    excludeName = {
	        "org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"
	    }
	)
public class GamingGearsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamingGearsApplication.class, args);
		System.out.println("Running");
	}

}
