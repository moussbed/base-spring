package com.mb.spring;

import com.mb.spring.models.Account;
import com.mb.spring.repository.ExampleDao;
import com.mb.spring.services.ParentBean;
import com.mb.spring.services.ParentBeanDefinition;
import com.mb.spring.services.TodoService;
import com.mb.spring.services.WorkWithAop;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.UUID;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config-*.xml");

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

        WorkWithAop bean3 = applicationContext.getBean(WorkWithAop.class);
        bean3.accounting();

        //UserService bean1 = applicationContext.getBean(UserService.class);
       // System.out.println(bean1.getCurrentUser());

        ExampleDao bean4 = applicationContext.getBean(ExampleDao.class);
        bean4.deleteAllAccount();
        bean4.count();
        Arrays.asList(new Account(1200),new Account(34000),new Account(4500000),new Account(750))
                .forEach(account -> bean4.createAccount(account));

        Account modifyAccount = new Account(7900);
        modifyAccount.setUserId("ff7d9d5d-48e6-49cc-9161-fad9361ff244");
        bean4.updateAccount(modifyAccount);
        bean4.findAllsAccount();


    }
}
