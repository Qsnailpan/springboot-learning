package com.snail.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snail.jpa.RoleRepository;
import com.snail.jpa.UserRepository;
import com.snail.model.Role;
import com.snail.model.User;

/**
 *
 * @author snail
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<User> getUserList() {
		return userRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public User postUser(@RequestBody User user) {

		User model = userRepository.save(user);
		if (!user.getRoles().isEmpty()) {
			List<Role> roles = new ArrayList<Role>();
			for (Role role : user.getRoles()) {
				roles.add(roleRepository.findOne(role.getId()));
			}
			model.setRoles(roles);
		}
		return model;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable Long id) {
		return userRepository.findOne(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public User putUser(@PathVariable Long id, @RequestBody User user) {
		User u = userRepository.findOne(id);
		u.setMobile(user.getMobile());
		u.setFullname(user.getFullname());
		u.setPassword(user.getPassword());
		u.setUsername(user.getUsername());
		return u;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object deleteUser(@PathVariable Long id) {
		userRepository.delete(id);
		return null;
	}

	@RequestMapping(value = "/findByUsername", method = RequestMethod.GET)
	public Object findByUsername(@RequestParam(value = "username") String username) {
		// where username_=?
		return userRepository.findByUsername(username);
	}

	@RequestMapping(value = "/findByUsernameContains", method = RequestMethod.GET)
	public Object findByUsernameContains(@RequestParam(value = "username") String username) {
		// where username_ like ?
		return userRepository.findByUsernameContains(username);
	}

	@RequestMapping(value = "/findByUsernameAndPassword", method = RequestMethod.GET)
	public Object findByUsernameContains(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		// where username_=? and password_=?
		return userRepository.findByUsernameAndPassword(username, password);
	}

	@RequestMapping(value = "/findByUsernameStartsWith", method = RequestMethod.GET)
	public Object findByUsernameStartsWith(@RequestParam(value = "username") String username) {
		// where username_ like ? order by fullname_ asc
		return userRepository.findByUsernameStartsWith(username, new Sort(Direction.ASC, "fullname"));
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<User> getUserPageList(
			@PageableDefault(page = 0, size = 10, sort = "username_", direction = Direction.DESC) Pageable pageable) {

		/**
		 * page 页数 - 默认0 >第一页 size 每页的数量 - 默认10 sort 排序的字段 - 默认空 direction 排序规则
		 * - 默认 asc
		 * 
		 * @PageableDefault
		 */
		// http://localhost:8080/users/page?page=0&size=3&sort=username_,asc

		// from bf_user_ order by username_ desc limit ?
		return userRepository.findAll(pageable);
	}

}