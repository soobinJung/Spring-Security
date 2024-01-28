package com.soobin.api.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @PostMapping("/sign-up")
    public String signUp() {
        return "회원가입";
    }

    @PostMapping("/sign-in")
    public String signIn() {
        return "로그인";
    }
}
