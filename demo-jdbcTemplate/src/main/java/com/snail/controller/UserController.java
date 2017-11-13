package com.snail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snail.dao.UserDao;
import com.snail.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 *
 * @author snail
 * @version 1.0.0
 *
 */
@Api(value = "/users", description = "用户接口")
@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserDao userDao;

	@ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
	@RequestMapping(method = RequestMethod.POST)
	public Boolean postUser(@ApiParam(value = "用户模型数据", required = true) @RequestBody User user) {
		
		return userDao.save(user);
	}

	@ApiOperation(value = "获取用户列表", notes = "获取所有用户列表")
	@RequestMapping(method = RequestMethod.GET)
	public List<User> getUserList() {
		List<User> r = userDao.findAll();
		return r;
	}

	@ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUser(@ApiParam(value = "用户ID", required = true) @PathVariable Long id) {

		return userDao.findOne(id);
	}

	@ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Boolean deleteUser(@ApiParam(value = "用户ID", required = true) @PathVariable Long id) {

		return userDao.delete(id);
	}
}