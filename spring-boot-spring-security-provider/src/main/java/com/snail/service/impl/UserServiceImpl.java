package com.snail.service.impl;

import com.snail.jpa.RoleRepository;
import com.snail.jpa.UserRepository;
import com.snail.model.Role;
import com.snail.model.User;
import com.snail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public User save(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder ();

        ArrayList<Role> roles = new ArrayList<> ();

        roles.add (roleRepository.findByName ("ROLE_USER"));
        user.setRoles (roles);

        user.setPassword (bCryptPasswordEncoder.encode (user.getPassword ().trim ()));

        return userRepository.save (user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername (username);
    }

    @Override
    public User findOne(long id) {
        return userRepository.findOne (id);
    }
}
