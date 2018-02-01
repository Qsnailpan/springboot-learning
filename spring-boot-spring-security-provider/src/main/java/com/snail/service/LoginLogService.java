package com.snail.service;

import com.snail.model.LoginLog;

import java.util.List;

/**
 * @classDesc: 功能描述:(...)
 * @author: lipan
 * @createTime: 2018/1/20
 */
public interface LoginLogService {

    LoginLog save(LoginLog model);

    List<LoginLog> findAll();



}
