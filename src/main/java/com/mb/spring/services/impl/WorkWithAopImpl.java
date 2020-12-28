package com.mb.spring.services.impl;

import com.mb.spring.services.WorkWithAop;
import org.springframework.stereotype.Component;


@Component
public class WorkWithAopImpl implements WorkWithAop {

    @Override
    public void accounting() {
        System.out.println("ACCOUNTING");
    }
}
