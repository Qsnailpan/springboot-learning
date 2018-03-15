package com.snail.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.snail.model.City;

/**
 * @Title: CityDao.java
 * @Package com.snail.dao
 * @Description: TODO( mybatis 通过配置文件使用  CityMapper.xml )
 * @author lipan
 * @date 2018年3月15日
 * @version V1.0
 */
public interface CityDaoXml {

	/**
	 * 获取城市信息列表
	 *
	 * @return
	 */
	List<City> findAllCity();

	/**
	 * 根据城市 ID，获取城市信息
	 *
	 * @param id
	 * @return
	 */
	City findById(@Param("id") Long id);

	Long saveCity(City city);

	Long updateCity(City city);

	Long deleteCity(Long id);
}
