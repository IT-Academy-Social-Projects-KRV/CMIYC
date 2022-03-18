package com.ms.authority.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class AdminController {

    @GetMapping
    public String hey(Principal principal) {
        return "hello, world!";
    }
}
