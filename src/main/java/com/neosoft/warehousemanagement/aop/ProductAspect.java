package com.neosoft.warehousemanagement.aop;

import com.neosoft.warehousemanagement.controller.ProductController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ProductAspect {
    private static final Logger logger = LoggerFactory.getLogger(ProductAspect.class);

    @Before("execution(* com.neosoft.warehousemanagement.controller.ProductController.*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        logger.info("Entering method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.neosoft.warehousemanagement.controller.ProductController.*(..))")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "execution(* com.neosoft.warehousemanagement.controller.ProductController.*(..))", throwing = "exception")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {} with message: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}
