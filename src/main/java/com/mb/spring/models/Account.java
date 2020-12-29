package com.mb.spring.models;

import java.util.Date;
import java.util.UUID;

public class Account {

    private String userId;

    private Date creationDate;

    private int balance;

    public Account() {
    }

    public Account(int balance) {
        this(UUID.randomUUID().toString(),new Date(),balance);
    }

    public Account(String userId, Date creationDate, int balance) {
        this.userId = userId;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
