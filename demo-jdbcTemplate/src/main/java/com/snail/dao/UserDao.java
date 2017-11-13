package com.snail.dao;

import java.util.List;

import com.snail.model.User;

public interface UserDao {

	List<User> findAll();
	
	Boolean save(User user);

	User findOne(Long id);

	User findByName(Long id);

	boolean delete(Long id);
}
