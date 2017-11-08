package com.snail.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.snail.dto.UserDto;
import com.snail.service.UserService;

@Configuration
public class InitDataConfig {
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	UserService userService;
	/**
	 * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，
	 * 
	 * 类似于Serclet的init()方法。被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@PostConstruct
	public void init() throws JsonParseException, JsonMappingException, IOException {
		// 初始用户信息
		if (!userService.exists()) {
			List<UserDto> areas = getListFromJson("user", UserDto.class);
			userService.save(areas);
		}
	}
	private <T> List<T> getListFromJson(String name, Class<T> c)
			throws JsonParseException, JsonMappingException, IOException {
		InputStream iStream = null;
		iStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/initdata/" + name + ".json");
		CollectionLikeType type = objectMapper.getTypeFactory().constructCollectionLikeType(List.class, c);
		return objectMapper.readValue(iStream, type);
	}
}
