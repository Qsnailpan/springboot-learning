package com.snail.dao;

import java.util.List;
import java.util.Map;

import com.snail.model.User;

public interface UserDao{
	
	List<User> findAll();
	
	User findOne(Long id);
	
	User findByName(Long id);
	
	boolean delete(Long id);
}
