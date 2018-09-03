package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author: lipan 2018年6月12日
 * @description: (请求拦截，实现IP 白|黑名单)
 * 
 */

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	WebInterceptor webInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/* 指定拦截的url */
		registry.addInterceptor(webInterceptor).addPathPatterns("/**");
	}
}
