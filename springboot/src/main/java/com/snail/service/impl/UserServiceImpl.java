package com.snail.service.impl;

import java.util.List;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snail.convert.UserConverter;
import com.snail.dto.UserDto;
import com.snail.model.User;
import com.snail.repository.UserRepository;
import com.snail.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	
	/**
     * 缓存的key
     */
    public static final String THING_ALL_KEY   = "\"user_all\"";
    /**
     * value属性表示使用哪个缓存策略，缓存策略在ehcache.xml
     */
    public static final String DEMO_CACHE_NAME = "demo";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserConverter userConverter;
	
	@Transactional  // 加上事务
	@Override
	public User save(UserDto dto) {
		if(Objects.isNull(dto)){
			return null;
		}
		User model = new User();
		// 复制dto属性到 实体model
		userConverter.copyProperties(model, dto);
		return userRepository.save(model);
	}
	@Override
	public User delete(long id) {
		 userRepository.delete(id);
		 return null;
	}

	@Override
	public User update(long id, UserDto dto) {
		User user = userRepository.getOne(id);
		userConverter.copyProperties(user, dto);
		return user;
	}

	@Override
	public User get(long id) {
		return userRepository.getOne(id);
	}
	
	@Cacheable(value = DEMO_CACHE_NAME,key = THING_ALL_KEY)
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	@Override
	public boolean exists() {
		return userRepository.count() > 0;
	}
	@Override
	public void save(List<UserDto> areas) {
		for (UserDto userDto : areas) {
			this.save(userDto);
		}
	}
	
}
