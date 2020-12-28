package com.mb.spring.config.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AfterThrowingExample {

    @AfterThrowing( pointcut="@annotation(com.mb.spring.annotation.Secure)" , throwing= "e")
    public void exceptionHandler(Exception e) {
        System.out.println("--------------Exception-------------------");
        System.out.println(e);
    }

}
