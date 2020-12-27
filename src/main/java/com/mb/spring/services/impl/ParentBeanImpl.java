package com.mb.spring.services.impl;

import com.mb.spring.services.InnerBean;
import com.mb.spring.services.ParentBean;

public class ParentBeanImpl implements ParentBean {

    private InnerBean innerBean;

    public ParentBeanImpl() {
    }

    public void setInnerBean(InnerBean innerBean) {
        this.innerBean = innerBean;
    }

    @Override
    public void display() {
        innerBean.displayToScreen();
    }

}
