package com.ms.authority.controller;

import com.ms.authority.service.UserService;
import com.ms.authority.utils.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path="registration")
    public String register(@RequestBody RegistrationRequest request) {
        return userService.register(request);
    }
/** This is next task, we are skipping it for now

    @GetMapping(path="registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return register.confirmToken(token);
    }
    **/
}
