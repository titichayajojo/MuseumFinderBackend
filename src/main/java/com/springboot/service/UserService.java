package com.springboot.service;

import com.springboot.entity.User;
import com.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password, String email){
        if (username!=null && password!=null){
            return null;
        }
        else{
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            return userRepository.save(user);
        }
    }

    public User authenticate(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
}
