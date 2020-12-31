package com.mb.spring.services;

import com.mb.spring.models.Todo;

public interface TodoService2 {
    void createTodo(Todo todo);

    Todo findTodo(String todoId);

    Todo updateTodo(String todoId, String description);

    void deleteTodo(String todoId);
}
