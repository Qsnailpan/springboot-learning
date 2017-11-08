package com.snail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snail.model.User;
import com.snail.service.UserService;


/**
 *
 * @author snail
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value="/users")     
public class UserController {
	
	@Autowired
	private UserService userService;
	
    @RequestMapping(method=RequestMethod.GET)
    public List<User> getUserList() {
        return userService.findAll();
    }
}