package com.snail.handler;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.snail.model.LoginLog;
import com.snail.service.LoginLogService;

/**
 * @classDesc: 功能描述:(登录成功日志记录)
 * @author: lipan
 * @createTime: 2018/1/2
 */

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private LoginLogService loginLogService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		LoginLog log = new LoginLog();
		log.setIp(request.getRemoteAddr());
		log.setTime(Calendar.getInstance().getTime());
		log.setType("Login");
		log.setUser(authentication.getName());
		loginLogService.save(log);

		// 登录成功后 重定向到 /home
		response.sendRedirect("/home");
	}
}
