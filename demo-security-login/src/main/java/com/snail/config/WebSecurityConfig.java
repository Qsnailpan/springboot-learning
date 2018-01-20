package com.snail.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 
 * @author snail  2018年1月3日
 *
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 匹配 "/" 路径，不需要权限即可访问 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限 
	 *    authorizeRequests().antMatchers("/").permitAll()
	 * 登录地址为"/login"，登录成功默认跳转到页面 "/user"
	 * 
	 * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
	 * 
	 * 默认启用CSRF(后续研究)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").permitAll()
			.antMatchers("/user/**").hasRole("USER")
			.and().formLogin() .loginPage("/login").defaultSuccessUrl("/user")
			.and().logout().logoutUrl("/logout") .logoutSuccessUrl("/login");
	}

	/**
	 * 在内存中创建一个名为 "root" 的用户，密码为 "root"，拥有 "USER" 权限
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.inMemoryAuthentication().withUser("root").password("root").roles("USER");
	}

}