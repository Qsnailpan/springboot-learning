
package com.snail.security.config;

import org.springframework.security.core.AuthenticationException;

/**
 * @Title: AuthenticationErrorService.java
 * @Package com.snail.config
 * @Description: TODO(用一句话描述该文件做什么)
 * @author lipan
 * @date 2018年1月2日
 * @version V1.0
 */
public interface AuthenticationErrorService {
	/**
	 * 增加用户错误计数
	 */
	public void error(String username, AuthenticationException exception);

	/**
	 * 清除用户的错误计数
	 * 
	 * @param username
	 */
	public void clearError(String username);

	/**
	 * 根据用户名获取错误信息
	 * 
	 * @param username
	 * @return
	 */
	public ErrorInfo get(String username);
}
