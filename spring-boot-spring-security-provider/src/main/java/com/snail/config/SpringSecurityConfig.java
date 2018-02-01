package com.snail.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.snail.handler.LoginFailureHandler;
import com.snail.security.config.CustomAuthenticationProvider;
import com.snail.security.service.CustomUserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 认证失败时 处理
	 */
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Resource
	AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	LogoutHandler logoutHandler;

	@Bean
	UserDetailsService customUserService() {
		return new CustomUserService();
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/", "/home", "/about", "/css/**", "/js/**", "/webjars/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN").antMatchers("/user/**").hasAnyRole("USER").anyRequest()
				.authenticated()

				.and().formLogin().loginPage("/login").successHandler(authenticationSuccessHandler)
				.failureHandler(new LoginFailureHandler()).permitAll().and().logout().addLogoutHandler(logoutHandler)
				.permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

		// session  会话管理
		// maximumSessions设置每个用户最大会话数，
		// maxSessionsPreventsLogin:为true时会阻止新用户上线，false时会踢掉原来会话,建议设置为false,默认是false
		// 因为当用户直接关闭浏览器时，并不一定会发出登出请求，这就导致服务器的session不会失效。从而导致新的会话不能上线
		// expiredUrl：当用户session失效时，会发送302响应到指定的地址。我们需要处理相关的请求
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).expiredUrl("/login");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
		// 如果使用了密码加密算法就需要 ， 这里我们使用的是Bcrypt 算法加密
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		// 需要一个 UserDetailsService (必须)
		authenticationProvider.setUserDetailsService(customUserService());
		/*
		authenticationProvider.setMaxError(2);
		authenticationProvider.setMaxLockMilliseconds(30000);
		*/
		// 开启使用本地provider
		auth.authenticationProvider(authenticationProvider);
		
		
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customUserService()).passwordEncoder(bCryptPasswordEncoder());
	}
}
