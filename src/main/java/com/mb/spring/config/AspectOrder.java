package com.mb.spring.config;


import org.springframework.core.Ordered;

public class AspectOrder implements Ordered {

    @Override
    public int getOrder() {
        return 0;
    }
}
