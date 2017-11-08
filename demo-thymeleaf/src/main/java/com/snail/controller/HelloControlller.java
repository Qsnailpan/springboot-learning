package com.snail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloControlller {
	
	@RequestMapping("/")
	public String index (ModelMap map){
		map.addAttribute("name", "I'm thymeleaf");
		return "index";
	}

}
