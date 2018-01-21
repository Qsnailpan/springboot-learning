package com.snail.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.snail.jpa.UserRepository;
import com.snail.model.Role;
import com.snail.model.User;
import com.snail.security.bean.SecurityUser;


/**
 * 本类需要实现 org.springframework.security.core.userdetails.UserDetailsService 接口
 *
 * 然后覆盖重写 loadUserByUsername(String userName) 方法
 * 在该方法内部，需要添加 userName,passWord,权限集合,其他参数 到我们 SecurityUser
 */
public class CustomUserService implements UserDetailsService {


    private Logger logger = LoggerFactory.getLogger (CustomUserService.class);
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info ("收到用户名：" + username);
        User user = userRepository.findByUsername (username);
        if (user == null) {
            throw new UsernameNotFoundException ("username 不存在");
        }

        return new SecurityUser (user.getUsername (), user.getPassword (), createGrantedAuthority (user.getRoles ()));
    }

    // 装载用户权限 信息
    List<GrantedAuthority> createGrantedAuthority(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority> ();

        for (Role role : roles) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority (role.getName ());// 权限实体
            authorities.add (grantedAuthority); // 增加到权限队列中
        }
        return authorities;
    }


}
