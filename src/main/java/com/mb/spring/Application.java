package com.mb.spring;

import com.mb.spring.services.TodoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");

        TodoService bean = applicationContext.getBean(TodoService.class);
        bean.findAssignedTodos().forEach(System.out::println);

    }
}
