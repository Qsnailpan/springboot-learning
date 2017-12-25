package com.snail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snail.jpa.RoleRepository;
import com.snail.model.Role;

/**
 *
 * @author snail
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value = "/roles")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<Role> getRoleList() {
		return roleRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Role postRole(@RequestBody Role Role) {
		return roleRepository.save(Role);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Role getRole(@PathVariable Long id) {
		return roleRepository.findOne(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Role putRole(@PathVariable Long id, @RequestBody Role role) {
		Role u = roleRepository.findOne(id);
		u.setDescribe(role.getDescribe());
		return u;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object deleteRole(@PathVariable Long id) {
		roleRepository.delete(id);
		return null;
	}

}