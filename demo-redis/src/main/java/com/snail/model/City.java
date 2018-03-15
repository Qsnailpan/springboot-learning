
package com.snail.model;

import java.io.Serializable;

/**
 * @Title: City.java
 * @Package com.snail.model
 * @Description: TODO(用一句话描述该文件做什么)
 * @author lipan
 * @date 2018年3月15日
 * @version V1.0
 */
public class City implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * 城市编号
	 */
	private Long id;

	/**
	 * 省份编号
	 */
	private Long provinceId;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 描述
	 */
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "City{" + "id=" + id + ", provinceId=" + provinceId + ", cityName='" + cityName + '\''
				+ ", description='" + description + '\'' + '}';
	}

}
