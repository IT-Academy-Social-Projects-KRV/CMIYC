package com.ms.authority.controller;

import java.util.List;

import javax.validation.Valid;

import com.ms.authority.dto.ConfirmRegisterData;
import com.ms.authority.dto.RegistrationRequest;
import com.ms.authority.dto.RegistrationResult;
import com.ms.authority.dto.UserDto;
import com.ms.authority.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "registration")
    public RegistrationResult register(@RequestBody RegistrationRequest request) {
        return userService.register(request);
    }

    /**
     * This is next task, we are skipping it for now
     *
     * @GetMapping(path="registration/confirm") public String confirm(@RequestParam("token") String token) {
     * return register.confirmToken(token);
     * }
     **/

    @PostMapping("/activation")
    public void confirmRegister(@Valid @RequestBody ConfirmRegisterData confirmRegisterData) {
        userService.confirmRegister(confirmRegisterData);
    }

    @PostMapping("/{userId}/enable")
    public void enableUser(@PathVariable int userId) {
        userService.changeUserActive(userId, true);
    }

    @PostMapping("/{userId}/disable")
    public void disableUser(@PathVariable int userId) {
        userService.changeUserActive(userId, false);
    }

    @GetMapping
    public List<UserDto> listUsersRequest() {
        return userService.listUsersRequest();
    }
}
