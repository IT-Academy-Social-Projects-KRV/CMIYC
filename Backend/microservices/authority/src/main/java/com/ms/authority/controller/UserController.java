package com.ms.authority.controller;

import com.ms.authority.service.UserService;
import com.ms.authority.dto.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import com.ms.authority.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "registration")
    public String register(@RequestBody RegistrationRequest request) {
        return userService.register(request);
    }

    /**
     * This is next task, we are skipping it for now
     *
     * @GetMapping(path="registration/confirm") public String confirm(@RequestParam("token") String token) {
     * return register.confirmToken(token);
     * }
     **/

    @PostMapping("/{userId}/enable")
    public void enableUser(@PathVariable int userId) {
        userService.changeUserActive(userId, true);
    }

    @PostMapping("/{userId}/disable")
    public void disableUser(@PathVariable int userId) {
        userService.changeUserActive(userId, false);
    }

    @GetMapping
    public Set<UserDto> listUsersRequest() {
        return userService.listUsersRequest();
    }
}
