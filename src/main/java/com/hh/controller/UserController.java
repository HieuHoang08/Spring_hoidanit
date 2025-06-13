package com.hh.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.domain.User;
import com.hh.repository.UserRepository;
import com.hh.service.UserService;

@Controller

public class UserController {

    private final UserService userService;

    public UserController(UserService userService ) {
        this.userService = userService;
    }
    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUserByEmail("1@gmail.com");
        System.out.println("List of users: " + arrUsers);
        model.addAttribute("message", "message");
        return "hello";
    }
    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }
    @RequestMapping("/admin/user/create1")
    public String createUserPage(Model model, @ModelAttribute("newUser") User newUser) {
        System.out.println("Creating user: " + newUser);
        this.userService.handleSaveUser(newUser);
        return "hello";
    }
}



