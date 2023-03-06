package com.weather.forecaster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ForecasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForecasterApplication.class, args);
	}

}
