package com.snail.security.bean;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
public class SecurityUser extends User {
	private static final long serialVersionUID = -254576396255401176L;

	
	//  可以附加其他信息 
	// private String mail
	
	
	public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
}
