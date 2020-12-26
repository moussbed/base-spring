package com.mb.spring.services;

import com.mb.spring.models.User;

public interface UserRepository {

    User getUserCurrent(String name);
}
