package com.ms.authority.controller;

import com.ms.authority.dto.ConfirmRegisterData;
import com.ms.authority.dto.RegistrationRequestData;
import com.ms.authority.dto.RegistrationResultData;
import com.ms.authority.dto.UserData;
import com.ms.authority.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "registration")
    public RegistrationResultData register(@RequestBody RegistrationRequestData request) {
        return userService.register(request);
    }

    @PostMapping("/activation")
    public void confirmRegister(@Valid @RequestBody ConfirmRegisterData confirmRegisterData) {
        userService.confirmRegister(confirmRegisterData);
    }

    @PostMapping("/{userId}/active/{newState}")
    public void enableUser(@PathVariable("userId") int userId, @PathVariable("newState") boolean newState) {
        userService.changeUserActive(userId, newState);
    }

    @GetMapping
    public List<UserData> listUsersRequest() {
        return userService.listUsersRequest();
    }
}
