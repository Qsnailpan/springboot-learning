package com.snail.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.snail.jpa.RoleRepository;
import com.snail.jpa.UserRepository;
import com.snail.model.Role;
import com.snail.model.User;

@Configuration
public class InitDataConfig {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，
	 * 
	 * 类似于Serclet的init()方法。被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@PostConstruct
	public void init() throws JsonParseException, JsonMappingException, IOException {
		// 初始用户信息
		if (userRepository.count() < 1) {
			Role role1 = new Role(null, "admin", "超级管理员");
			Role role2 = new Role(null, "root", "管理员");

			roleRepository.save(role1);
			roleRepository.save(role2);

			List<User> users = new ArrayList<User>();
			for (int i = 0; i < 10; i++) {
				List<Role> roles = new ArrayList<Role>();

				User user = new User();
				user.setUsername("zhangsan" + i);
				user.setFullname("张三" + i);
				user.setMobile("12345-" + i);
				user.setPassword("666666" + i);
				if (i % 2 == 0) {
					roles.add(role1);
				} else {
					roles.add(role2);
				}

				user.setRoles(roles);
				users.add(user);
			}
			userRepository.save(users);

		}
	}

}
