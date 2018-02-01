package com.snail.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snail.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {


    Role findByName(String user);
}
