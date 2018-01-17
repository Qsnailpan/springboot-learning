package com.snail.demoaspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DemoAspectApplication {

    public static void main(String[] args) {
        SpringApplication.run (DemoAspectApplication.class, args);

    }
}
