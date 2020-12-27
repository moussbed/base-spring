package com.mb.spring.data;

import com.google.common.collect.Sets;
import com.mb.spring.models.Todo;
import com.mb.spring.models.TodoList;
import com.mb.spring.models.User;

import java.util.*;

public abstract class AbstractDataSource {

    private  static Map<String, User> userList = new TreeMap<>();

    static {
        User user = new User().setName("Bedril");
        User user2 = new User().setName("Moussakat");
        Set<Todo> todos = Sets.newHashSet(
                new Todo().setAssignedUser(user),
                new Todo().setAssignedUser(user2).setCompleted(true)
        );
        TodoList todoList = new TodoList()
                .setTodos(todos);
        List<TodoList> todoLists = new ArrayList<>();
        todoLists.add(todoList);

        user.setTodoLists(todoLists);
        userList.put("Bedril", user);
    }

    public static Map<String, User> getUserList() {
        return userList;
    }

    public Optional<User> getUserByName(String name) {
        return Optional.ofNullable(userList.get(name));
    }

}
