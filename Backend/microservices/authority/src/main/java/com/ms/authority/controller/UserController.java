package com.ms.authority.controller;

import com.ms.authority.dto.ConfirmRegisterData;
import com.ms.authority.dto.RegistrationRequestData;
import com.ms.authority.dto.RegistrationResultData;
import com.ms.authority.dto.UserData;
import com.ms.authority.entity.User;
import com.ms.authority.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "registration")
    public void register(@RequestBody RegistrationRequestData request) {
        userService.register(request);
    }

    @PostMapping("/activation")
    public void confirmRegister(@Valid @RequestBody ConfirmRegisterData confirmRegisterData, HttpServletRequest request) {
        userService.confirmRegister(confirmRegisterData, request.getRemoteAddr());
    }

    @PostMapping("/{userId}/active/{newState}")
    public void enableUser(@PathVariable("userId") int userId, @PathVariable("newState") boolean newState) {
        userService.changeUserActive(userId, newState);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") User user) {
        userService.deleteUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") User user, @RequestBody RegistrationRequestData request) {
        return userService.updateUserById(user, request);
    }

    @GetMapping
    public Page<UserData> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/search")
    public Page<UserData> findUserByParams(@RequestParam(defaultValue = "") String email,
                                           @RequestParam(defaultValue = "") String firstName,
                                           @RequestParam(defaultValue = "") String lastName,
                                           @RequestParam(defaultValue = "true") Boolean isActive,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return userService.findUserByParams(email, firstName, lastName, isActive, page, size);
    }
}
