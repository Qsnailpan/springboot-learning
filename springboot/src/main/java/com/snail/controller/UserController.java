package com.snail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snail.commons.ResultDto;
import com.snail.convert.UserConverter;
import com.snail.dto.UserDto;
import com.snail.service.UserService;

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
	@Autowired
	private UserService userService;
	@Autowired
	private UserConverter userConverter;
	
    @ApiOperation(value="获取用户列表", notes="获取所有用户列表")
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public ResultDto<List<UserDto>> getUserList() {
    	try {
    		return ResultDto.success(userConverter.toDto(userService.findAll()));
		} catch (Exception e) {
			return ResultDto.failure(e.getMessage());
		}
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @RequestMapping(method=RequestMethod.POST)
    public ResultDto<UserDto> postUser(
    		@ApiParam(value = "用户模型数据") @RequestBody UserDto user) {
    	try {
    		 return ResultDto.success(userConverter.toDto(userService.save(user)));
		} catch (Exception e) {
			return ResultDto.failure(e.getMessage());
		}
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResultDto<UserDto> getUser(@ApiParam(value = "用户ID", required = true)@PathVariable Long id) {
    	try {
    		return ResultDto.success(userConverter.toDto(userService.get(id)));
		} catch (Exception e) {
			return ResultDto.failure(e.getMessage());
		}
    }
    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResultDto<UserDto> putUser(
    		@ApiParam(value = "用户ID" , required = true)@PathVariable Long id, 
    		@ApiParam(value = "用户模型数据", required = true)@RequestBody UserDto user) {
    	try {
    		return ResultDto.success(userConverter.toDto(userService.update(id, user)));
		} catch (Exception e) {
			return ResultDto.failure(e.getMessage());
		}
    }
    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResultDto<?> deleteUser(
    		@ApiParam(value = "用户ID", required = true)@PathVariable Long id) {
    	try{
    		 userService.delete(id);
    		 return ResultDto.success("删除成功");
    	}catch (Exception e) {
    		 return ResultDto.failure(e.getMessage());
		}
    }
}