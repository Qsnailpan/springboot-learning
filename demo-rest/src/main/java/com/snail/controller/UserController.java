package com.snail.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value="/users")     
public class UserController {
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());
    @ApiOperation(value="获取用户列表", notes="获取所有用户列表")
    @RequestMapping(method=RequestMethod.GET)
    public List<User> getUserList() {
        List<User> r = new ArrayList<User>(users.values());
        return r;
    }
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @RequestMapping(method=RequestMethod.POST)
    public User postUser(
    		@ApiParam(value = "用户模型数据", required = true) @RequestBody User user) {
        users.put(user.getId(), user);
        return user;
    }
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public User getUser(@ApiParam(value = "用户ID", required = true)@PathVariable Long id) {
        return users.get(id);
    }
    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public User putUser(
    		@ApiParam(value = "用户ID", required = true)@PathVariable Long id, 
    		@ApiParam(value = "用户模型数据" , required = true)@RequestBody User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        return u;
    }
    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(
    		@ApiParam(value = "用户ID", required = true)@PathVariable Long id) {
        users.remove(id);
        return null;
    }
}