
package com.snail.security.config;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Title: CustomAuthenticationProvider.java
 * @Package com.snail.config
 * @Description: TODO(自定义 provider进行认证 ，进行登录次数限制)
 * @author lipan
 * @date 2018年1月2日
 * @version V1.0
 */
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

	private AuthenticationErrorService errorService = new InMemoryAuthenticationErrorService();
	private int maxError = 3;
	private long maxLockMilliseconds = 1 * 60 * 1000;
	

	/**
	 * @param maxError the maxError to set
	 */
	public void setMaxError(int maxError) {
		this.maxError = maxError;
	}
	/**
	 * @param maxLockMilliseconds the maxLockMilliseconds to set
	 */
	public void setMaxLockMilliseconds(long maxLockMilliseconds) {
		this.maxLockMilliseconds = maxLockMilliseconds;
	}

	Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		try {
			logger.info("开始本地自定义认证....");

			// 认证前判断用户是否被错误次数锁定
			if (!validate(username)) {
				throw new ErrorOutOfMaxException("账号已被锁定🔒！  " + (maxLockMilliseconds / 60000) + "分钟后自定解锁！");
			}
			// 认证
			Authentication auth = super.authenticate(authentication);
			// 认证成功，清除用户锁定计数
			errorService.clearError(username);
			return auth;
		} catch (BadCredentialsException e) {
			ErrorInfo error = errorService.get(username);
			if (error != null) {
				throw new BadCredentialsException("登录失败！您还有  " + (maxError - error.getErrCount()) + "次机会");
			}
			throw new BadCredentialsException("登录失败!");
		} catch (AuthenticationException e) {
			throw e;
		}
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		try {
			super.additionalAuthenticationChecks(userDetails, authentication);
		} catch (BadCredentialsException e) {
			// 密码校验错误，增加错误计数
			errorService.error(userDetails.getUsername(), e);
			throw e;
		} catch (AuthenticationException e) {
			throw e;
		}
	}

	/**
	 * 验证错误次数是否超限
	 * 
	 * @param error
	 * @return
	 */
	private boolean validate(String username) {
		ErrorInfo error = errorService.get(username);
		if (error == null) {
			return true;
		}
		long now = Calendar.getInstance().getTime().getTime();
		long lastError = error.getLastErrorTime().getTime();
		if (now - lastError >= maxLockMilliseconds) {
			errorService.clearError(username);
			return true;
		} else {
			return error.getErrCount() < maxError;
		}
	}
}
