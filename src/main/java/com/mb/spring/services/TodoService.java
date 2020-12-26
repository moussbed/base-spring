package com.mb.spring.services;

import com.mb.spring.models.Todo;

import java.util.Collection;

public interface TodoService {
    Collection<Todo> findAssignedTodos();
}
