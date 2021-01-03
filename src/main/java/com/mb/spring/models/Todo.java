package com.mb.spring.models;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;


@Entity
public class Todo implements Comparable<Todo>{

    @Id
    @Column(name = "id")
    @NotNull
    @ISBN
    private String todoId;

    private User assignedUser;

    private boolean completed;

    @Column(name = "creation_date")
    @Past
    private Date creationDate;

    private String description;

    @Min(0)
    @NotNull
    private int priority;

    public User getAssignedUser() {
        return assignedUser;
    }

    public Todo setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
        return this;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Todo setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public String getTodoId() {
        return todoId;
    }

    public Todo setTodoId(String todoId) {
        this.todoId = todoId;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Todo setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Todo setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public Todo setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public int compareTo(Todo o) {
        return this.getPriority()-o.getPriority();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Todo)) return false;

        Todo todo = (Todo) o;

        return new EqualsBuilder()
                .append(completed, todo.completed)
                .append(priority, todo.priority)
                .append(todoId, todo.todoId)
                .append(assignedUser, todo.assignedUser)
                .append(creationDate, todo.creationDate)
                .append(description, todo.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(todoId)
                .append(assignedUser)
                .append(completed)
                .append(creationDate)
                .append(description)
                .append(priority)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("todoId", todoId)
                .append("assignedUser", assignedUser)
                .append("completed", completed)
                .append("creationDate", creationDate)
                .append("description", description)
                .append("priority", priority)
                .toString();
    }
}
