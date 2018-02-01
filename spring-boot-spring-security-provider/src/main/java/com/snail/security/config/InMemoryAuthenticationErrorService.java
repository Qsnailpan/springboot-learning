
package com.snail.security.config;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.springframework.security.core.AuthenticationException;

/**
 * @Title: InMemoryAuthenticationErrorService.java
 * @Package com.snail.config
 * @Description: TODO(用一句话描述该文件做什么)
 * @author lipan
 * @date 2018年1月2日
 * @version V1.0
 */
public class InMemoryAuthenticationErrorService implements AuthenticationErrorService {

	private final HashMap<String, ErrorInfo> errorStorage = new HashMap<>();

	private final WriteLock writeLock;
	private final ReadLock readLock;
	
	{
		ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
		writeLock = rwLock.writeLock();
		readLock = rwLock.readLock();
	}

	@Override
	public void error(String username, AuthenticationException exception) {
		try {
			writeLock.lock();
			ErrorInfo info = errorStorage.get(username);
			if (info == null) {
				info = new ErrorInfo(username);
				errorStorage.put(username, info);
			}
			int count = info.getErrCount();
			info.setErrCount(++count);
			info.setLastErrorTime(Calendar.getInstance().getTime());
		} finally {
			writeLock.unlock();
		}

	}

	@Override
	public void clearError(String username) {
		try {
			writeLock.lock();
			errorStorage.remove(username);
		} finally {
			writeLock.unlock();
		}
	}

	public boolean validate(String username, int maxError, long maxLockMilliseconds) {
		long now = Calendar.getInstance().getTime().getTime();
		try {
			readLock.lock();
			ErrorInfo info = errorStorage.get(username);
			if (info == null) {
				return true;
			} else {
				long lastError = info.getLastErrorTime().getTime();
				if (now - lastError >= maxLockMilliseconds) {
					clearError(info.getUsername());
					return true;
				} else {
					return info.getErrCount() < maxError;
				}
			}
		} finally {
			readLock.unlock();
		}

	}

	@Override
	public ErrorInfo get(String username) {
		try {
			readLock.lock();
			return errorStorage.get(username);
		} finally {
			readLock.unlock();
		}
	}

}
