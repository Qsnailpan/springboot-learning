package com.snail.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
     * <p>
     * 类似于Serclet的init()方法。被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
     */
    @PostConstruct
    public void init() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder ();
        // 初始用户信息
        if (userRepository.count () < 1) {
            Role role1 = new Role (null, "ROLE_ADMIN", "超级管理员");
            Role role2 = new Role (null, "ROLE_USER", "用户");

            roleRepository.save (role1);
            roleRepository.save (role2);

            User admin = new User ();
            admin.setUsername ("admin");
            admin.setFullname ("admin");
            admin.setMobile ("12345-");
            admin.setPassword (bCryptPasswordEncoder.encode ("root"));

            User user = new User ();
            user.setUsername ("user");
            user.setFullname ("user");
            user.setMobile ("12345-");
            user.setPassword (bCryptPasswordEncoder.encode ("root"));


            // 添加admin 用户 拥有 ROLE_ADMIN 权限
            List<Role> adminRoleList = new ArrayList<Role> ();
            adminRoleList.add (role1);
            admin.setRoles (adminRoleList);

            // 添加user 用户 拥有 ROLE_USER 权限
            List<Role> userRoleList = new ArrayList<Role> ();
            userRoleList.add (role2);
            user.setRoles (userRoleList);


            userRepository.save (admin);
            userRepository.save (user);

        }
    }

}
