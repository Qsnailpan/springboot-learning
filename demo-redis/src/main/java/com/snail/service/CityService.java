
package com.snail.service;

import com.snail.model.City;

/**
 * @Title: CityService.java
 * @Package service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author lipan
 * @date 2018年3月15日
 * @version V1.0
 */
public interface CityService {
	/**
	 * 根据城市 ID,查询城市信息
	 *
	 * @param id
	 * @return
	 */
	City findCityById(Long id);

	/**
	 * 新增城市信息
	 *
	 * @param city
	 * @return
	 */
	Long saveCity(City city);

	/**
	 * 更新城市信息
	 *
	 * @param city
	 * @return
	 */
	Long updateCity(City city);

	/**
	 * 根据城市 ID,删除城市信息
	 *
	 * @param id
	 * @return
	 */
	Long deleteCity(Long id);
}
