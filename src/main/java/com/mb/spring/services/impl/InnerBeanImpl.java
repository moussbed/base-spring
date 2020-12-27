package com.mb.spring.services.impl;

import com.mb.spring.services.InnerBean;

class InnerBeanImpl implements InnerBean {
    private String name;
    private Integer age;

    public InnerBeanImpl() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void displayToScreen() {
        System.out.println(name + " " + age);
    }
}
