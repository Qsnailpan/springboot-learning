package com.snail.service;

import java.util.List;

import com.snail.model.User;

public interface UserService {
	
	List<User> findAll();

	User findOne(Long id);

	User update(Long id, User user);
	
	void del(Long id);
	
	User save(User user);
}
