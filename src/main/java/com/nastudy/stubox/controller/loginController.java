package com.nastudy.stubox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

    @GetMapping("/auth/login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/")
    public String login(){
        return "main";
    }
}
