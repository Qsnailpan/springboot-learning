package com.snail.commons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * controller返回值
 * 
 * @author snail
 *
 */
public class ResultDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3166291416871323434L;
	public boolean success;
	private T data;
	private String error;

	/**
	 * 标记返回是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 返回的数据
	 * 
	 * @return
	 */
	@JsonInclude(value = Include.NON_NULL)
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@JsonInclude(value = Include.NON_NULL)
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public static <T> ResultDto<T> success(T data) {
		ResultDto<T> response = new ResultDto<>();
		response.setSuccess(true);
		response.setData(data);
		return response;
	}

	public static <T> ResultDto<T> failure(String error) {
		ResultDto<T> response = new ResultDto<>();
		response.setSuccess(false);
		response.setError(error);
		return response;
	}

	public static ResultDto<Void> success() {
		return ResultDto.success(null);
	}

	public static ResultDto<Void> failure() {
		return ResultDto.failure(null);
	}
}
