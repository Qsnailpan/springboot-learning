package com.snail.service.impl;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
	@Cacheable(cacheNames = "user", sync = true)
	public List<User> findAll() {
		logger.info("findAll 没有走缓存!");
		return userRepository.findAll();
	}

	@Cacheable(cacheNames = "user", sync = true)
	@Override
	public User findOne(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	@CacheEvict(allEntries = true, value = { "user" })
	@Transactional
	public User update(Long id, User user) {
		logger.info("update  清空 key:all 缓存!");
		Optional<User> entity = userRepository.findById(id);
		if (entity.isPresent()) {
			entity.get().setFullname(user.getFullname());
			entity.get().setUsername(user.getUsername());
			entity.get().setPassword(user.getPassword());
			return entity.get();
		}
		return null;
	}

	@Override
	@CacheEvict(allEntries = true, value = { "user" })
	@Transactional
	public void del(Long id) {
		logger.info("del  清空缓存!");
		userRepository.deleteById(id);
	}

	@Override
	@CacheEvict(allEntries = true, value = { "user" })
	@Transactional
	public User save(User user) {
		logger.info("save  清空 key:all 缓存!");
		return userRepository.save(user);
	}

}
