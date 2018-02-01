package com.snail.service.impl;

import com.snail.jpa.LoginLogRepository;
import com.snail.model.LoginLog;
import com.snail.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @classDesc: 功能描述:(...)
 * @author: lipan
 * @createTime: 2018/1/23
 */

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogRepository loginLogRepository;

    @Override
    @Transactional
    public LoginLog save(LoginLog model) {

        return loginLogRepository.save (model);
    }

    @Override
    public List<LoginLog> findAll() {

        return loginLogRepository.findAll ();
    }
}
