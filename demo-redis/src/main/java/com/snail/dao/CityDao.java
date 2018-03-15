
package com.snail.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.snail.model.City;

/**
 * @Title: CityDao.java
 * @Package com.snail.dao
 * @Description: TODO(mybatis 全注解使用 )
 * @author lipan
 * @date 2018年3月15日
 * @version V1.0
 */
@Mapper
public interface CityDao {
	/**
	 * 获取城市信息列表
	 *
	 * @return
	 */
	@Select("SELECT * FROM city")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "provinceId", column = "province_id"),
			@Result(property = "description", column = "description"),
			@Result(property = "cityName", column = "city_name") })
	List<City> findAllCity();

	/**
	 * 根据城市 ID，获取城市信息
	 *
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM city WHERE id = #{id}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "provinceId", column = "province_id"),
			@Result(property = "description", column = "description"),
			@Result(property = "cityName", column = "city_name") })
	City findById(@Param("id") Long id);

	@Insert("INSERT INTO city(id,description,city_name,province_id) VALUES(#{id}, #{description}, #{cityName}, #{provinceId})")
	Long saveCity(City city);

	@Update("UPDATE city SET city_name=#{cityName},province_id=#{provinceId},province_id=#{provinceId} WHERE id =#{id}")
	Long updateCity(City city);

	@Delete("DELETE FROM city WHERE id =#{id}")
	Long deleteCity(Long id);
}
