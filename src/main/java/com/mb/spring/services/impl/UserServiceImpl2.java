package com.mb.spring.services.impl;

import com.mb.spring.models.User;
import com.mb.spring.services.UserRepository;
import com.mb.spring.services.UserService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserServiceImpl2 implements UserService {

    @Inject
    private  UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        //return userRepository.getUserCurrent("Saly");
        return userRepository.getUserCurrent("Bedril");
    }
}

