package com.yanyun.redis.exception;

import com.yanyun.redis.model.BaseResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 统一返回结果切面
 */
@Aspect
@Component
public class ResponseAspect {
    @Around("execution(* com.yanyun.redis.controller..*(..))")
    public Object controllerProcess(ProceedingJoinPoint pjd) throws Throwable {
        Object result = pjd.proceed();
        //如果controller不返回结果
        if (result == null) {
            return BaseResponse.success(null);
        }
        return BaseResponse.success(result);
    }
}
