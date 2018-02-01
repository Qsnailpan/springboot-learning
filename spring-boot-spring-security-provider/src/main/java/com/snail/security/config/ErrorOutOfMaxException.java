
package com.snail.security.config;

import org.springframework.security.core.AuthenticationException;

/**
 * @Title: ErrorOutOfMaxException.java
 * @Package com.snail.security.error
 * @Description: TODO(用一句话描述该文件做什么)
 * @author lipan
 * @date 2018年1月2日
 * @version V1.0
 */
public class ErrorOutOfMaxException extends AuthenticationException {

	/**
	 * @param msg
	 */
	public ErrorOutOfMaxException(String msg) {
		super(msg);
	}

	public ErrorOutOfMaxException() {
		super("登录次数上限！ ");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
