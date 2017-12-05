package com.snail.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.snail.jpa.UserRepository;
import com.snail.model.User;
import com.snail.service.UserService;

@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	Logger logger = Logger.getLogger(getClass());

	@Override
	@Cacheable
	public List<User> findAll() {
		logger.info("没有走缓存!");
		return userRepository.findAll();
	}

}
