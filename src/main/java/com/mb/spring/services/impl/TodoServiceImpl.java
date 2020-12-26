package com.mb.spring.services.impl;

import com.mb.spring.models.Todo;
import com.mb.spring.models.TodoList;
import com.mb.spring.models.User;
import com.mb.spring.services.TodoService;
import com.mb.spring.services.UserService;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class TodoServiceImpl implements TodoService {

    private UserService userService;

    @Override
    public Collection<Todo> findAssignedTodos() {
        User currentUser = userService.getCurrentUser();
        Set<Todo> assignedTodos = new TreeSet<>();
        for (TodoList todoList : currentUser.getTodoLists()) {
            for (Todo todo : todoList.getTodos()) {
                if (todo.getAssignedUser() != null && todo.getAssignedUser().equals(currentUser) && !todo.isCompleted()) {
                    assignedTodos.add(todo);
                }
            }
        }

        return assignedTodos;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService=userService;
    }
}
