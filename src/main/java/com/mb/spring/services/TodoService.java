package com.mb.spring.services;

import com.mb.spring.models.Todo;

import java.util.Collection;
import java.util.Map;

public interface TodoService {

    Collection<Todo> findAssignedTodos();

    Collection<String> findEmailsList();

    Map<Long, String> findTownList();

    String userName();


}
