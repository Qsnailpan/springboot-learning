package com.snail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.snail.model.User;

public interface UserRepository extends JpaRepository<User, Long>,QueryDslPredicateExecutor<User>{
	
	

}
