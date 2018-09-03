package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
	Logger looger = LoggerFactory.getLogger(DefaultController.class);

	@GetMapping("/")
	public String index() {
		return "/";
	}

	@GetMapping("/home")
	public String home() {
		return "/home";
	}
}
