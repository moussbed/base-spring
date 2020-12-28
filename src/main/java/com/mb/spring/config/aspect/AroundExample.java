package com.mb.spring.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@Aspect
public class AroundExample {

    @Around("execution(* com.mb.spring.services.impl.*.*(..))")
    public Object profileMethods(ProceedingJoinPoint pjp) throws Throwable {
        long start = Calendar.getInstance().getTimeInMillis();
        Object result = pjp.proceed();
        System.out.println(Calendar.getInstance().getTimeInMillis() - start);
        return result;
    }
}
