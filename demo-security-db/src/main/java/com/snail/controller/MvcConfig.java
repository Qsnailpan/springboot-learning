package com.snail.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *   可 替代 Homecontroller
 *   
 * @author snail  2018年1月11日
 *
 */

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
   /* 
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/common/tes").setViewName("common/test");
    }
    */
}
