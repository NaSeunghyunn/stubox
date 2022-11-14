package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

    @GetMapping("/auth/login")
    public String loginForm() {
        return "/loginForm";
    }

    @GetMapping("/")
    public String login(@AuthenticationPrincipal PrincipalDetail principal){
        return "/main";
    }
}
