package com.snail.service.impl;

import com.snail.jpa.UserRepository;
import com.snail.model.Role;
import com.snail.model.User;
import com.snail.security.bean.SecurityUser;
import com.snail.service.UserService;
import com.snail.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;


/**
 * 本类需要实现 org.springframework.security.core.userdetails.UserDetailsService 接口
 * 然后覆盖重写 loadUserByUsername(String userName) 方法
 * 在该方法内部，需要添加 userName,passWord,权限集合,其他参数 到我们 SecurityUser
 */
public class CustomUserService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println ("录入的username值为：" + username);

        User user = userRepository.findByUsername (username);

        if (user == null) {
            throw new UsernameNotFoundException ("username 不存在");
        }
        List<Role> roleList = user.getRoles ();

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority> ();

        for (Role role : roleList) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority (role.getName ());// 权限实体
            authorities.add (grantedAuthority); // 增加到权限队列中
        }

        return new SecurityUser (user.getUsername (), user.getPassword (), authorities);
    }

}
