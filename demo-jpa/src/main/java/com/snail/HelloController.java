package com.snail;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
   *  Created by snail  2017年9月7日
   *  
   */

@RestController
@RequestMapping("/index")
public class HelloController {
	
	@RequestMapping("/hello")
	private String hello(){
		return "Hello SpringBoot";
	}

}
