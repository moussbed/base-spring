package com.mb.spring.models;

import java.util.Set;

public class TodoList {

    private Set<Todo> todos ;

    public Set<Todo> getTodos() {
        return todos;
    }

    public TodoList setTodos(Set<Todo> todos) {
        this.todos = todos;
        return this;
    }
}
