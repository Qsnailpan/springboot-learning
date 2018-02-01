
package com.snail.security.config;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @Title: ErrorInfo.java
 * @Package com.snail.security.error
 * @Description: TODO(用一句话描述该文件做什么)
 * @author lipan
 * @date 2018年1月2日
 * @version V1.0
 */
@Data
public class ErrorInfo implements Serializable {
	private static final long serialVersionUID = -1691845203749560635L;

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 错误总数
	 */
	private volatile int errCount;

	/**
	 * 最后出错时间
	 */
	private Date lastErrorTime;

	public ErrorInfo(String username) {
		this.username = username;
	}

}
