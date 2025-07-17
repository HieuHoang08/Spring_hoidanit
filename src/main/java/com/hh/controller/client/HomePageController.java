package com.hh.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hh.domain.Product;
import com.hh.domain.User;
import com.hh.domain.dto.RegisterDTO;
import com.hh.service.ProductService;
import com.hh.service.UserService;

import jakarta.validation.Valid;


@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder PasswordEncoder;

    public HomePageController(
            ProductService productService,
            UserService userService,
            PasswordEncoder PasswordEncoder) {

        this.productService = productService;
        this.userService = userService;
        this.PasswordEncoder = PasswordEncoder;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(
        @ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
        BindingResult bindingResult
        ) {

            if(bindingResult.hasErrors()) {
                return "client/auth/register";
            }
        User user = this.userService.getRegisterDTO(registerDTO);

        String hashPassword = this.PasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));
        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {

        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage(Model model) {

        return "client/auth/deny";
    }
}
