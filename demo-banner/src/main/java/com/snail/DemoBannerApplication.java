package com.snail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoBannerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(DemoBannerApplication.class);
		// 关闭banner
//		application.setBannerMode(Mode.OFF);
		application.run(args);
	}
}
