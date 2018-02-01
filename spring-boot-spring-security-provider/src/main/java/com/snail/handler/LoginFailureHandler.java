package com.snail.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @classDesc: 功能描述:(登录失败，返回错误信息)
 * @author: lipan
 * @createTime: 2018/1/2
 */

public class LoginFailureHandler implements AuthenticationFailureHandler {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(403);
		
		Map<String, String> datas = new HashMap<String, String>();
		datas.put("error", exception.getMessage());

		String error = objectMapper.writeValueAsString(datas);
		response.getWriter().write(error);
	}

}
