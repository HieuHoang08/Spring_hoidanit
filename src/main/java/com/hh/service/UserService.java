package com.hh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hh.domain.Role;
import com.hh.domain.User;
import com.hh.domain.dto.RegisterDTO;
import com.hh.repository.RoleRepository;
import com.hh.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final  RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public User handleSaveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public List<User> getAllUserByEmail(String email){
        return this.userRepository.findOneByEmail(email);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }
    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User getRegisterDTO(RegisterDTO registerDTO){
        User user = new User();

        user.setFullname(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email){
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
