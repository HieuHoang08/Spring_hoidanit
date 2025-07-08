package com.hh.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hh.domain.User;
import com.hh.service.UploadService;
import com.hh.service.UserService;

import jakarta.validation.Valid;

@Controller

public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder PasswordEncoder;

    public UserController(
            UserService userService,
            UploadService uploadService,
            PasswordEncoder PasswordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.PasswordEncoder = PasswordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUserByEmail("1@gmail.com");
        model.addAttribute("message", "message");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        // Assuming you would fetch user by ID and add to model
        // For now, just returning a placeholder view
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("userId", id);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User newUser,
            BindingResult newUserBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file
            ) {
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        //validate email
        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }
        String avatar = this.uploadService.handleSaveFile(file, "avatar");
        String hashPassword = this.PasswordEncoder.encode(newUser.getPassword());
        newUser.setAvatar(avatar);
        newUser.setPassword(hashPassword);
        newUser.setRole(this.userService.getRoleByName(newUser.getRole().getName()));
        this.userService.handleSaveUser(newUser);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User cUser = this.userService.getUserById(id);
        model.addAttribute("newUser", cUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUserPage(Model model, @ModelAttribute("newUser") User newUser) {
        User updatedUser = this.userService.getUserById(newUser.getId());
        if (updatedUser != null) {
            updatedUser.setAddress(newUser.getAddress());
            updatedUser.setFullname(newUser.getFullname());
            updatedUser.setPhone(newUser.getPhone());
            this.userService.handleSaveUser(updatedUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String deleteUser(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        User user = new User();
        user.setId(id);
        model.addAttribute("newUser", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete/{id}")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User newUser) {
        User user = this.userService.getUserById(newUser.getId());
        this.userService.deleteUserById(user.getId());
        return "redirect:/admin/user";
    }
}
