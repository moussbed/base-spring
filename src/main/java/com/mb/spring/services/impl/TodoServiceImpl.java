package com.mb.spring.services.impl;

import com.mb.spring.models.Todo;
import com.mb.spring.models.TodoList;
import com.mb.spring.models.User;
import com.mb.spring.services.TodoService;
import com.mb.spring.services.UserService;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

public class TodoServiceImpl implements TodoService {

    private UserService userService;

    private List<String> emails;

    private Map<Long,String> towns;

    @Value(value = "${dataSource.username}")
    private String username;

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

    @Override
    public String userName() {
        return username;
    }

    @Override
    public Collection<String> findEmailsList() {
        Collections.sort(emails);
        return emails;
    }

    @Override
    public Map<Long, String> findTownList() {
        return towns ;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void setTowns(Map<Long, String> towns) {
        this.towns = towns;
    }
}
