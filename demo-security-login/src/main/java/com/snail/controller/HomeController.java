package com.snail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * @author snail  2018年1月3日
 *
 */
@Controller
public class HomeController {

	@GetMapping({ "/" })
	public String root() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

}
