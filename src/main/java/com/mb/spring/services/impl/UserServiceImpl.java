package com.mb.spring.services.impl;

import com.mb.spring.models.User;
import com.mb.spring.services.UserRepository;
import com.mb.spring.services.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        return userRepository.getUserCurrent("Bedril");
    }
}
