package com.mb.spring.models;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Todo implements Comparable<Todo>{

    private User assignedUser;
    private boolean completed;

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

    public Todo
    setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Todo)) return false;

        Todo todo = (Todo) o;

        return new EqualsBuilder()
                .append(completed, todo.completed)
                .append(assignedUser, todo.assignedUser)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(assignedUser)
                .append(completed)
                .toHashCode();
    }

    @Override
    public int compareTo(Todo o) {
        return assignedUser.getName().compareTo(o.assignedUser.getName());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("assignedUser", assignedUser)
                .append("completed", completed)
                .toString();
    }
}
