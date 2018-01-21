package com.snail.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

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
	 */
	@PostConstruct
	public void init() {
		// 初始用户信息
		if (userRepository.count() < 1) {
			Role role1 = new Role(null, "ROLE_USER", "用户");
			Role role2 = new Role(null, "ROLE_ADMIN", "管理员");

			roleRepository.save(role1);
			roleRepository.save(role2);

			List<Role> roles = new ArrayList<Role>();
			User user = new User();
			user.setUsername("root");
			user.setFullname("张三");
			user.setMobile("12345-");
			user.setPassword("root");

			roles.add (role2);
			user.setRoles(roles);

			userRepository.save(user);

		}
	}

}
