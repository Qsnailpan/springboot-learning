package com.snail.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(value = "/users")
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public List<User> findAll() {
		try {
			return userService.findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Collections.emptyList();
		}
	}

	@PostMapping
	@Transactional
	public User save(@RequestBody User user) {
		try {
			return userService.save(user);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@GetMapping(value = "{id}")
	public User getOne(@PathVariable(value = "id") Long id) {
		try {
			return userService.findOne(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}

	}

	@PutMapping(value = "{id}")
	public User update(@PathVariable(value = "id") Long id, @RequestBody User user) {
		try {
			return userService.update(id, user);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@DeleteMapping(value = "{id}")
	public boolean del(@PathVariable(value = "id") Long id) {
		try {
			userService.del(id);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
}