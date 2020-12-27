package com.mb.spring;

import com.mb.spring.services.ParentBean;
import com.mb.spring.services.ParentBeanDefinition;
import com.mb.spring.services.TodoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");

        TodoService bean = applicationContext.getBean(TodoService.class);
        bean.findAssignedTodos().forEach(System.out::println);
        System.out.println(bean.findEmailsList());
        System.out.println(bean.findTownList());
        System.out.println(bean.userName());

        ParentBean bean1 = applicationContext.getBean(ParentBean.class);
        bean1.display();
        ParentBeanDefinition bean2 = applicationContext.getBean(ParentBeanDefinition.class);
        System.out.println(bean2.getAge());
        System.out.println(bean2.getName());

        //UserService bean1 = applicationContext.getBean(UserService.class);
       // System.out.println(bean1.getCurrentUser());

    }
}
