package com.snail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DemoJcacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJcacheApplication.class, args);
	}

}
