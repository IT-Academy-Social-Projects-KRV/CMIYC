package com.ms.authority.controller;

import com.ms.authority.entity.User;
import com.ms.authority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerNewUser")
    public User registerNewUser() {
        // return userService.registerNewUser(someData...);
        return null;
    }

    @PostMapping("/{userId}/enable")
    public User enableUser(@RequestParam String userId) {
        return userService.changeUserActive(userId, true);
    }

    @PostMapping("/{userId}/disable")
    public User disableUser(@RequestParam String userId) {
        return userService.changeUserActive(userId, false);
    }
}
