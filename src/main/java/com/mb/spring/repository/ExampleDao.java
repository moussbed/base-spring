package com.mb.spring.repository;

import com.mb.spring.models.Account;

import java.util.List;

public interface ExampleDao {

    void count();

    void createAccount(Account account);

    void updateAccount(Account account);

    void deleteAllAccount();

    List<Account> findAllsAccount();
}
