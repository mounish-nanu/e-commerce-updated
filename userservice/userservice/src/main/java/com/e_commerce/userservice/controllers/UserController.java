package com.e_commerce.userservice.controllers;

import com.e_commerce.userservice.models.User;
import com.e_commerce.userservice.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("users")
    public String addUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    @PostMapping("login")
    public String login(@RequestBody User user) {
        return userService.loginUser(user);
    }
    @PostMapping("logout/{token}")
    public String logout(@PathVariable String token) {
        return userService.deleteSession(token);
    }
}
