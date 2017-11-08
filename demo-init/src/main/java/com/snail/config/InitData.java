package com.snail.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Configuration;

@Configuration
public class InitData {
	
//  工程启动时运行
	@PostConstruct
	private void init(){
		System.out.println("I'm  init  method  using  @PostConstrut....");
	}
	
//  工程关闭之前被运行
	@PreDestroy
	private void destory(){
		System.out.println("I'm  destory method  using  @PreDestroy.....");  
	}

}
