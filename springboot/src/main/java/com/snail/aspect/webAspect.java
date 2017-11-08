package com.snail.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
   *  Created by snail  2017年8月18日
   *  
   */
@Aspect
@Component
public class webAspect {

	
	private Logger logger = Logger.getLogger(getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    
    @Pointcut("execution(public * com.snail.controller..UserController.*(..))")
//    @Pointcut("execution(public * com.snail.controller..*.*(..))")
    public void webLog(){}
   

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("-----------接收到请求，记录请求内容------------");
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        logger.info("----------------           ----------------");
    }
    
    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        // 处理完请求，返回内容
    	logger.info("-----------处理完请求，返回内容--------------");
        logger.info("RESPONSE : " + result);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
        logger.info("----------------           ----------------");
    }
}
