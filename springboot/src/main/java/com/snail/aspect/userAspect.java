package com.snail.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.snail.model.User;

/**
   *  Created by snail  2017年8月18日
   *  
   */

@Aspect
@Component
public class userAspect {
	
	private Logger logger = Logger.getLogger(getClass());
	
    @AfterReturning(pointcut = "execution(public * com.snail.service.impl.UserServiceImpl.save(..))", returning = "result")
    public void afterSave(JoinPoint joinPoint, Object result) {
       // 处理完请求，返回内容
		
    	logger.info("-----------UserServiceImpl.save() 执行结束 ，开始切面任务--------------");
    	if(result instanceof User){
    		User user = (User)result;
    		user.setName("aspectName");
    		logger.info("-----------(将保存之后的姓名更改为-- ：" + user.getName());
    	}
    	
    	logger.info("-----------UserServiceImpl.save() 之后切面任务 结束--------------");
    }
}
