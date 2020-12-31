package com.mb.spring.services.impl;


import com.mb.spring.models.Todo;
import com.mb.spring.services.TodoService2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
public class TodoServiceImpl2 implements TodoService2 {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createTodo(Todo todo) {
        Date now = Calendar.getInstance().getTime();
        todo.setCreationDate(now);
        em.persist(todo);
    }
    @Override
    public Todo findTodo(String todoId) {
        return em.find(Todo.class, todoId);
    }
    @Override
    public Todo updateTodo(String todoId, String description) {
        Todo todo = em.find(Todo.class, todoId);
        todo.setDescription(description);
        return todo;
    }
    @Override
    public void deleteTodo(String todoId) {
        Todo todo = em.find(Todo.class, todoId);
        em.remove(todo);
    }
}
