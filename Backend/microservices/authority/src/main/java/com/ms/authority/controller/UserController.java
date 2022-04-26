package com.ms.authority.controller;

import java.util.List;

import javax.validation.Valid;

import com.ms.authority.dto.ConfirmRegisterData;
import com.ms.authority.dto.RegistrationRequest;
import com.ms.authority.dto.RegistrationResult;
import com.ms.authority.dto.UserDto;
import com.ms.authority.entity.User;
import com.ms.authority.service.UserService;

import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{userId}/active/{newState}")
    public void enableUser(@PathVariable("userId") int userId, @PathVariable("newState") boolean newState) {
        userService.changeUserActive(userId, newState);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable ("userId")User user){
        userService.deleteUser(user);
    }

    @PutMapping("/{userId}")
    public User updatedUser (@PathVariable ("userId") User user, @RequestBody RegistrationRequest request){
        return userService.updateUserById(user, request);
    }

    @GetMapping
    public List<UserDto> listUsersRequest() {
        return userService.listUsersRequest();
    }
}
