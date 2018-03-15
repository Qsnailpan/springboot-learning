
package com.snail.service.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.snail.dao.CityDao;
import com.snail.model.City;
import com.snail.service.CityService;

/**
 * @Title: CityServiceImpl.java
 * @Package com.snail.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author lipan
 * @date 2018年3月15日
 * @version V1.0
 */
@Service
public class CityServiceImpl implements CityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

	@Autowired
	private CityDao cityDao;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 获取城市逻辑： 如果缓存存在，从缓存中获取城市信息 如果缓存不存在，从 DB 中获取城市信息，然后插入缓存
	 */
	@Override
	public City findCityById(Long id) {
		// 从缓存中获取城市信息
		String key = "city_" + id;
		ValueOperations<String, City> operations = redisTemplate.opsForValue();
		// 缓存存在
		boolean hasKey = redisTemplate.hasKey(key);
		if (hasKey) {
			City city = operations.get(key);
			LOGGER.info(" 从缓存中获取了key >> " + city.toString());
			return city;
		}
		// 从 DB 中获取城市信息
		City city = cityDao.findById(id);
		// 插入缓存 10s 过期
		operations.set(key, city, 10, TimeUnit.SECONDS);
		LOGGER.info(" 插入缓存 >> " + city.toString());
		return city;
	}

	@Override
	public Long saveCity(City city) {
		LOGGER.info("保存成功 !");
		return cityDao.saveCity(city);
	}

	/**
	 * 更新城市逻辑： 如果缓存存在，删除 如果缓存不存在，不操作
	 */
	@Override
	public Long updateCity(City city) {
		Long ret = cityDao.updateCity(city);

		// 缓存存在，删除缓存
		String key = "city_" + city.getId();
		boolean hasKey = redisTemplate.hasKey(key);
		if (hasKey) {
			redisTemplate.delete(key);
			LOGGER.info(" 从缓存中删除key >> " + city.toString());
		}
		LOGGER.info("更新成功 !");
		return ret;
	}

	@Override
	public Long deleteCity(Long id) {

		Long ret = cityDao.deleteCity(id);

		// 缓存存在，删除缓存
		String key = "city_" + id;
		boolean hasKey = redisTemplate.hasKey(key);
		if (hasKey) {
			redisTemplate.delete(key);
			LOGGER.info(" 从缓存中删除 key >> " + id);
		}
		LOGGER.info("删除成功 !");
		return ret;
	}

}
