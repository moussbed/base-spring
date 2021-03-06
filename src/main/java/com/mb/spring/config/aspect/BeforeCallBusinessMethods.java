package com.mb.spring.config.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BeforeCallBusinessMethods {


    @Before("execution(* com.mb.spring.services.impl.*.*(..))")
    public void accesControl(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature());
        System.out.println("-------------------------------------------------");
        System.out.println("-----------------BEFORE ACCESS-------------------");
    }


}
