package com.neosoft.warehousemanagement.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerAspect.class);

    @Before("execution(* com.neosoft.warehousemanagement.controller.UserController.*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        logger.info("Entering method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.neosoft.warehousemanagement.controller.UserController.*(..))")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "execution(* com.neosoft.warehousemanagement.controller.UserController.*(..))", throwing = "exception")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {} with message: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}
