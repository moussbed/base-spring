package com.mb.spring.config.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class BeforeCallBusinessMethods {


    @Before("execution(* com.mb.spring.services.impl.*.*(..))")
    public void accesControl() {
        System.out.println("-------------------------------------------------");
        System.out.println("-----------------BEFORE ACCESS-------------------");
    }
}
