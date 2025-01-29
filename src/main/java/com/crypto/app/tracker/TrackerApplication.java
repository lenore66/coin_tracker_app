package com.crypto.app.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.crypto.app.tracker"})
@EnableAutoConfiguration
public class TrackerApplication {

	public static void main(String[] args) {

		SpringApplication.run(TrackerApplication.class, args);
	}

}
