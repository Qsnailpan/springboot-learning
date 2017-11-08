package com.snail.service;

import java.util.List;

import com.snail.commons.CrudService;
import com.snail.dto.UserDto;
import com.snail.model.User;

public interface UserService extends CrudService<User, UserDto> {
	List<User> findAll();

	boolean exists();

	void save(List<UserDto> areas);
}
