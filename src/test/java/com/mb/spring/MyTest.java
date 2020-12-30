package com.mb.spring;

import com.mb.spring.models.Todo;
import com.mb.spring.models.User;
import com.mb.spring.services.UserRepository;
import com.mb.spring.services.UserService;
import com.mb.spring.services.impl.UserServiceImpl;
import com.mb.spring.services.impl.UserServiceImpl2;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
public class MyTest {

    @Test
    public void testEquals(){
        Todo todo1 = new Todo();
        todo1.setCompleted(false);
        Todo todo2 = new Todo();
        todo2.setCompleted(false);
        assertEquals(todo1.isCompleted(),todo2.isCompleted());

        Todo todo3 = new Todo();
        todo3.setCompleted(true);

        assertNotSame(todo1.isCompleted(),todo3.isCompleted());
    }

    @Test
    public void testUserService1(){

     UserService userService = new UserServiceImpl(new UserRepository() {
         @Override
         public User getUserCurrent(String name) {
             return new User().setName("Bedril");
         }
     });

     assertEquals(userService.getCurrentUser().getName(),"Bedril");

    }

    @Test
    public void testUserService2(){

        UserService userService = new UserServiceImpl2();

        ReflectionTestUtils.setField(userService, "userRepository", new UserRepository() {
            @Override
            public User getUserCurrent(String name) {
                return new User().setName("Bedril");
            }
        });
        assertEquals(userService.getCurrentUser().getName(),"Bedril");

    }
}
