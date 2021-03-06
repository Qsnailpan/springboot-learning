package com.snail.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.snail.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	
	User findByUsername(String username);
	
	List<User> findByUsernameStartsWith(String username , Sort sort);
	
	List<User> findByUsernameContains(String username);
	
	List<User> findByUsernameAndPassword(String username , String password);
	
}
