package com.hh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hh.domain.User;
import com.hh.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User handleSaveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
    public List<User> getAllUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }


}
