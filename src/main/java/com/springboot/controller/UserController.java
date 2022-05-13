package com.springboot.controller;

import com.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserController {
    @Autowired
    UserRepository userRepository;
}
