package com.mb.spring.services.impl;

import com.mb.spring.data.AbstractDataSource;
import com.mb.spring.models.User;
import com.mb.spring.services.UserRepository;

public class UserRepositoryImpl extends AbstractDataSource implements UserRepository {

    @Override
    public User getUserCurrent(String name) {
        return getUserByName(name).orElseThrow(() -> new IllegalArgumentException(name));
    }
}
