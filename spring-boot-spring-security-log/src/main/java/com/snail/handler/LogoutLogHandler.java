package com.snail.handler;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.snail.model.LoginLog;
import com.snail.service.LoginLogService;

/**
 * @classDesc: 功能描述:(退出登录日志记录)
 * @author: lipan
 * @createTime: 2018/1/2
 */
@Component
public class LogoutLogHandler implements LogoutHandler {

	@Autowired
	private LoginLogService loginLogService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		LoginLog log = new LoginLog();
		log.setIp(request.getRemoteAddr());
		log.setTime(Calendar.getInstance().getTime());
		log.setType("Logout");
		log.setUser(authentication.getName());
		loginLogService.save(log);
	}
}
