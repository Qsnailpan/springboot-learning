package com.snail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DemoEhCacheApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoEhCacheApplication.class, args);
	}
}
