package com.snail.jpa;

import com.snail.model.LoginLog;
import com.snail.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {


}
