package com.snail.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snail.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	List<User> findByUsername(String username);
	
	
}
