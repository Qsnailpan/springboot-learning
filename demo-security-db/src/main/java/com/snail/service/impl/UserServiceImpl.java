package com.snail.service.impl;

import com.snail.jpa.RoleRepository;
import com.snail.jpa.UserRepository;
import com.snail.model.Role;
import com.snail.model.User;
import com.snail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @classDesc: 功能描述:(...)
 * @author: lipan
 * @createTime: 2018/1/20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User save(User user) {

        ArrayList<Role> roles = new ArrayList<> ();

        roles.add (roleRepository.findByName ("ROLE_USER"));
        user.setRoles (roles);

        return userRepository.save (user);
    }
}
