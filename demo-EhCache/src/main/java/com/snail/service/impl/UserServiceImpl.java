package com.snail.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snail.jpa.UserRepository;
import com.snail.model.User;
import com.snail.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	Logger logger = Logger.getLogger(getClass());

	@Override
	@Cacheable(value = "user", key = "'all'")   //  主要这里的  key = 'all' (也可使用双引号，需要 \" 转义)
	public List<User> findAll() {
		logger.info("findAll 没有走缓存!");
		return userRepository.findAll();
	}

	@Override
	@Cacheable(value = "user", key = "#id")
	public User findOne(Long id) {
		logger.info("findOne 没有走缓存!");
		return userRepository.findOne(id);
	}

	@Override
	@CachePut(value = "user", key = "#id")
	@CacheEvict(value = "user", key = "'all'")
	@Transactional
	public User update(Long id, User user) {
		logger.info("update  清空 key:all 缓存!");
		User entity = userRepository.findOne(id);
		entity.setFullname(user.getFullname());
		entity.setUsername(user.getUsername());
		entity.setPassword(user.getPassword());
		return entity;
	}

	@Override
	@CacheEvict(value = "user", allEntries = true, beforeInvocation = true)
	@Transactional
	public void del(Long id) {
		logger.info("del  清空缓存!");
		userRepository.delete(id);
	}

	@Override
	@CacheEvict(value = "user", key = "'all'")
	@Transactional
	public User save(User user) {
		logger.info("save  清空 key:all 缓存!");
		return userRepository.save(user);
	}

}
