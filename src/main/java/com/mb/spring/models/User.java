package com.mb.spring.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class User {
    private String name;

    private List<TodoList> todoLists;

    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public User setTodoLists(List<TodoList> todoLists) {
        this.todoLists = todoLists;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("todoLists", todoLists)
                .toString();
    }
}
